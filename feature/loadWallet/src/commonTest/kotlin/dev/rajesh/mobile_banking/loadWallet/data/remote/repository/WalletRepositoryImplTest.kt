package dev.rajesh.mobile_banking.loadWallet.data.remote.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.data.mapper.toWalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.data.repository.WalletRepositoryImpl
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletServiceChargeUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.WalletLoadUseCase
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeValidationResponse
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletList
import dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel.LoadWalletViewModel
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.kotest.matchers.shouldBe
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

//    @Test
//    fun should_return_wallet_list_when_success()= runTest{
//        val expectedResult = fakeWalletList()
//
//        remoteDataSource.getWalletList()
//        advanceUntilIdle()
//
//        when(val result = repository.getWalletList()) {
//            is ApiResult.Error -> throw AssertionError("Expected success, got error: ${result.error}")
//            is ApiResult.Success->{
//                result.data shouldBe expectedResult
//            }
//        }
//
//    }

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
    fun should_return_charge_when_success() = runTest {
        val mockChargeResponse =
    }

}