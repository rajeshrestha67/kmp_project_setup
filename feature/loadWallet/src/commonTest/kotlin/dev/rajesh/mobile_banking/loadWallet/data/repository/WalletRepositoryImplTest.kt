package dev.rajesh.mobile_banking.loadWallet.data.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletListResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.dto.WalletLoadResponseDTO
import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.data.repository.WalletRepositoryImpl
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletLoadDetails
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletServiceChargeUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.WalletLoadUseCase
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeLoadWalletSuccessResponse
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeValidationResponse
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletCharge
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletList
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletListResponse
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.LoadWalletViewModel
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test


@OptIn(ExperimentalCoroutinesApi::class)
class WalletRepositoryImplTest {
    private lateinit var remoteDataSource: WalletRemoteDataSource

    private lateinit var repository: WalletRepositoryImpl
    private lateinit var walletViewModel: LoadWalletViewModel

    private val fakeUser: UserDetails = dev.rajesh.mobile_banking.testUtils.fakes.fakeUser

    private lateinit var selectedAccountStore: SelectedAccountStore

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        remoteDataSource = mock()
        repository = WalletRepositoryImpl(remoteDataSource)

        selectedAccountStore = SelectedAccountStore().apply {
            set(fakeUser.accountDetail.get(0))
        }

        walletViewModel = LoadWalletViewModel(
            requiredValidationUseCase = RequiredValidationUseCase(),
            validateWalletUseCase = ValidateWalletUseCase(repository),
            getWalletServiceChargeUseCase = GetWalletServiceChargeUseCase(repository),
            walletLoadUseCase = WalletLoadUseCase(repository),
            selectedAccountStore = selectedAccountStore
        )
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun should_return_wallet_list_when_success()= runTest{
        val fakeWalletList = fakeWalletListResponse()
        val fakeWalletResponse = WalletListResponseDTO(
            status = "success",
            message = "Wallet fetch successfully",
            code = "M001",
            details = fakeWalletList
        )
        everySuspend {
            remoteDataSource.getWalletList()
        } returns ApiResult.Success(fakeWalletResponse)

        val result = repository.getWalletList()

        result.shouldBeInstanceOf<ApiResult.Success<List<WalletDetail>>>()

        val data = result.data
        data shouldHaveSize 2

        data[0].id shouldBe  1
        data[0].name shouldBe "eSewa"


    }

    @Test
    fun validateWallet_should_return_success_when_validation_succeeds() = runTest {
        val fakeValidationResponse = fakeValidationResponse()
        everySuspend {
            remoteDataSource.validateWallet(
                "walletId",
                "9840173991",
                "1000"
            )
        } returns ApiResult.Success(
            fakeValidationResponse
        )

        when (val result = repository.validateWallet("walletId", "9840173991", "1000")) {
            is ApiResult.Error -> throw AssertionError("Expected success, got error: ${result.error}")

            is ApiResult.Success -> {
                val expectedResult = fakeValidationResponse.detail.toWalletValidationDetail()
                result.data shouldBe expectedResult
            }
        }
    }

    @Test
    fun should_return_error_when_validation_failed() = runTest {
        everySuspend {
            remoteDataSource.validateWallet(
                "walletId",
                "9840173991",
                "1000"
            )
        } returns ApiResult.Error(
            DataError.NetworkError.DataUnknown
        )

        val result = repository.validateWallet("walletId", "9840173991", "1000")
        result shouldBe ApiResult.Error(DataError.NetworkError.DataUnknown)

    }

    @Test
    fun validateWallet_should_return_charge_when_success() = runTest {
        val fakeWalletChargeResponse = fakeWalletCharge()
        everySuspend {
            remoteDataSource.getWalletCharge(
                "1000",
                "walletId",
                "SERVICE"
            )
        } returns ApiResult.Success(fakeWalletChargeResponse)

        val result = repository.getWalletCharge("1000", "walletId", "SERVICE")

        when (result) {
            is ApiResult.Error -> {

            }

            is ApiResult.Success -> {
                val data = result.data
                data.details shouldBe 10.0

            }
        }
    }

    @Test
    fun validateWallet_should_show_error_when_fetch_charge_failed() = runTest {
        everySuspend {
            remoteDataSource.getWalletCharge(
                "1000",
                "walletId",
                "SERVICE"
            )
        } returns ApiResult.Error(DataError.NetworkError.DataUnknown)

        val result = repository.getWalletCharge("1000", "walletId", "SERVICE")
        result shouldBe ApiResult.Error(DataError.NetworkError.DataUnknown)
    }

    @Test
    fun should_return_success_when_load_wallet_succeeds() = runTest {

        val fakeWalletLoadResponse = fakeLoadWalletSuccessResponse()
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )
        everySuspend {
            remoteDataSource.walletLoad(walletLoadRequest)
        } returns ApiResult.Success(fakeWalletLoadResponse)

        val result = repository.walletLoad(walletLoadRequest)
        result.shouldBeInstanceOf<ApiResult.Success<WalletLoadDetails>>()

        val data = result.data
        data.message shouldBe "Successfully transferred to wallet"
    }

    @Test
    fun should_return_error_when_load_wallet_fails() = runTest {

        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )

        everySuspend {
            remoteDataSource.walletLoad(walletLoadRequest)
        } returns ApiResult.Error(DataError.NetworkError.DataUnknown)

        val result = repository.walletLoad(walletLoadRequest)
        result shouldBe ApiResult.Error(DataError.NetworkError.DataUnknown)
    }


}