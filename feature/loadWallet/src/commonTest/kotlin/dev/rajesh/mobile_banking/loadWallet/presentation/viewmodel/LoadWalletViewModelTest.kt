package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.spy
import dev.mokkery.verifySuspend
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletServiceChargeUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
import dev.rajesh.mobile_banking.loadWallet.domain.usecase.WalletLoadUseCase
import dev.rajesh.mobile_banking.loadWallet.fakes.FakeWalletRepository
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletChargeDetail
import dev.rajesh.mobile_banking.loadWallet.fakes.fakeWalletValidationDetail
import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.model.ErrorData
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.res.SharedRes
import dev.rajesh.mobile_banking.user.domain.model.UserDetails
import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.stopKoin
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class LoadWalletViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeWalletRepository: FakeWalletRepository

    private lateinit var requiredValidationUseCase: RequiredValidationUseCase
    private lateinit var validateWalletUseCase: ValidateWalletUseCase
    private lateinit var walletServiceChargeUseCase: GetWalletServiceChargeUseCase
    private lateinit var walletLoadUseCase: WalletLoadUseCase

    private val fakeUser: UserDetails = dev.rajesh.mobile_banking.testUtils.fakes.fakeUser
    private lateinit var selectedAccountStore: SelectedAccountStore

    private lateinit var viewModel: LoadWalletViewModel

    private var fakeWalletDetails = WalletDetail(
        id = 1,
        name = "eSewa",
        descOneFieldName = "Wallet ID",
        descOneFieldType = "String",
        descOneFixedLength = false,
        descOneLength = 0,
        descOneMinLength = 1,
        descOneMaxLength = 35,
        descTwoFieldName = "Remarks",
        descTwoFieldType = "String",
        descTwoFixedLength = false,
        descTwoLength = "",
        descTwoMinLength = 1,
        descTwoMaxLength = 35,
        icon = "1542352527067.png",
        accountHead = "ESEWA",
        accountNumber = "ESEWAWALLET",
        minAmount = 100.00,
        maxAmount = 1000.00,
        status = "Active"

    )

    private val amount = "1000.00"
    private val serviceOf = "Service"
    private val associatedId = "13"


    @BeforeTest
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)
        fakeWalletRepository = FakeWalletRepository()
        requiredValidationUseCase = RequiredValidationUseCase()
        validateWalletUseCase = ValidateWalletUseCase(fakeWalletRepository)
        walletServiceChargeUseCase = GetWalletServiceChargeUseCase(fakeWalletRepository)
        walletLoadUseCase = WalletLoadUseCase(fakeWalletRepository)

        selectedAccountStore = SelectedAccountStore().apply {
            set(fakeUser.accountDetail.get(0))
        }

        viewModel = LoadWalletViewModel(
            requiredValidationUseCase,
            validateWalletUseCase,
            walletServiceChargeUseCase,
            walletLoadUseCase,
            selectedAccountStore
        )

    }

    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun validateWallet_should_update_state_when_validation_fails() = runTest {
        viewModel.onAction(
            LoadWalletScreenAction.OnProceedClicked(walletDetail = fakeWalletDetails)
        )
        advanceUntilIdle()

        val state = viewModel.state.value
        state.walletIdError shouldBe SharedRes.Strings.required
        state.amountError shouldBe SharedRes.Strings.required
        state.remarksError shouldBe SharedRes.Strings.required
    }

    @Test
    fun validateWallet_should_call_fetch_charge_on_validation_success() = runTest {

        //Arrange
        fakeWalletRepository.errorOnValidateWallet = false

        viewModel.onAction(LoadWalletScreenAction.OnWalletIdChanged("9840173991"))
        viewModel.onAction(LoadWalletScreenAction.OnAmountChanged("1234"))

        //Act
        viewModel.onAction(
            LoadWalletScreenAction.OnProceedClicked(walletDetail = fakeWalletDetails)
        )

        advanceUntilIdle()

        //Assert
        val state = viewModel.state.value

        state.isValidatingWallet shouldBe false
        state.walletValidationError shouldBe null

        verifySuspend {
            validateWalletUseCase(
                fakeWalletDetails.id.toString(),
                "9840173991",
                "1234"
            )
        }

        //fetch charge
    }

    @Test
    fun validationWallet_should_show_confirmation_view_on_fetch_charge_success() = runTest {
        //Arrange
        fakeWalletRepository.errorOnServiceCharge = false

        viewModel.onAction(LoadWalletScreenAction.OnAmountChanged(amount))
        viewModel.onAction(LoadWalletScreenAction.OnWalletIdChanged("9840173991"))
        viewModel.onAction(LoadWalletScreenAction.OnRemarksChanged("remarks"))

        //act
        viewModel.fetchCharge(fakeWalletValidationDetail())

        advanceUntilIdle()

        //assert
        val state = viewModel.state.value
        state.isValidatingWallet shouldBe false
        state.charge shouldBe "10.0"
    }

    @Test
    fun validationWallet_should_show_error_view_on_fetch_charge_failed() = runTest {
        //Arrange
        fakeWalletRepository.errorOnServiceCharge = true

        viewModel.onAction(LoadWalletScreenAction.OnAmountChanged(amount))
        viewModel.onAction(LoadWalletScreenAction.OnWalletIdChanged("9840173991"))
        viewModel.onAction(LoadWalletScreenAction.OnRemarksChanged("remarks"))

        //act
        viewModel.fetchCharge(fakeWalletValidationDetail())

        advanceUntilIdle()
        //assert
        val state = viewModel.state.value
        state.isValidatingWallet shouldBe false
        state.walletValidationError?.message shouldBe "Something went wrong"
    }
}