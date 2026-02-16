//package dev.rajesh.mobile_banking.loadWallet.presentation.viewmodel
//
//import dev.mokkery.answering.returns
//import dev.mokkery.every
//import dev.mokkery.everySuspend
//import dev.mokkery.matcher.any
//import dev.mokkery.mock
//import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
//import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletDetail
//import dev.rajesh.mobile_banking.loadWallet.domain.model.WalletValidationDetail
//import dev.rajesh.mobile_banking.loadWallet.domain.usecase.GetWalletServiceChargeUseCase
//import dev.rajesh.mobile_banking.loadWallet.domain.usecase.ValidateWalletUseCase
//import dev.rajesh.mobile_banking.loadWallet.domain.usecase.WalletLoadUseCase
//import dev.rajesh.mobile_banking.loadWallet.fakes.FakeWalletRepository
//import dev.rajesh.mobile_banking.loadWallet.fakes.FakeWalletResponses
//import dev.rajesh.mobile_banking.loadWallet.presentation.state.LoadWalletScreenAction
//import dev.rajesh.mobile_banking.loadWallet.presentation.ui.LoadWalletScreen
//import dev.rajesh.mobile_banking.networkhelper.ApiResult
//import dev.rajesh.mobile_banking.res.SharedRes
//import dev.rajesh.mobile_banking.user.domain.model.AccountDetail
//import dev.rajesh.mobile_banking.user.domain.model.UserDetails
//import dev.rajesh.mobile_banking.useraccounts.presentation.state.SelectedAccountStore
//import io.kotest.matchers.shouldBe
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.advanceUntilIdle
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runTest
//import kotlinx.coroutines.test.setMain
//import org.koin.core.context.stopKoin
//import kotlin.test.AfterTest
//import kotlin.test.BeforeTest
//import kotlin.test.Test
//import kotlin.test.assertEquals
//
//@OptIn(ExperimentalCoroutinesApi::class)
//class LoadWalletViewModelTest {
//
//    private val testDispatcher = StandardTestDispatcher()
//
//    private lateinit var fakeWalletRepository: FakeWalletRepository
//
//    private lateinit var requiredValidationUseCase: RequiredValidationUseCase
//    private lateinit var validateWalletUseCase: ValidateWalletUseCase
//    private lateinit var walletServiceChargeUseCase: GetWalletServiceChargeUseCase
//    private lateinit var walletLoadUseCase: WalletLoadUseCase
//
//    private val fakeUser: UserDetails = dev.rajesh.mobile_banking.testUtils.fakes.fakeUser
//    private lateinit var selectedAccountStore: SelectedAccountStore
//
//    private lateinit var viewModel: LoadWalletViewModel
//
//    private var fakeWalletDetails = WalletDetail(
//        id = 1,
//        name = "eSewa",
//        descOneFieldName = "Wallet ID",
//        descOneFieldType = "String",
//        descOneFixedLength = false,
//        descOneLength = 0,
//        descOneMinLength = 1,
//        descOneMaxLength = 35,
//        descTwoFieldName = "Remarks",
//        descTwoFieldType = "String",
//        descTwoFixedLength = false,
//        descTwoLength = "",
//        descTwoMinLength = 1,
//        descTwoMaxLength = 35,
//        icon = "1542352527067.png",
//        accountHead = "ESEWA",
//        accountNumber = "ESEWAWALLET",
//        minAmount = 100.00,
//        maxAmount = 1000.00,
//        status = "Active"
//
//    )
//
//    @BeforeTest
//    fun setUp() = runTest {
//        Dispatchers.setMain(testDispatcher)
//        fakeWalletRepository = FakeWalletRepository()
//        requiredValidationUseCase = RequiredValidationUseCase()
//        validateWalletUseCase = ValidateWalletUseCase(fakeWalletRepository)
//        walletServiceChargeUseCase = GetWalletServiceChargeUseCase(fakeWalletRepository)
//        walletLoadUseCase = WalletLoadUseCase(fakeWalletRepository)
//
//        selectedAccountStore = SelectedAccountStore().apply {
//            set(fakeUser.accountDetail.get(0))
//        }
//
//
//        viewModel = LoadWalletViewModel(
//            requiredValidationUseCase,
//            validateWalletUseCase,
//            walletServiceChargeUseCase,
//            walletLoadUseCase,
//            selectedAccountStore
//        )
//
//    }
//
//    @AfterTest
//    fun tearDown() {
//        stopKoin()
//        Dispatchers.resetMain()
//    }
//
//    @Test
//    fun validateWallet_should_update_state_when_validation_fails() = runTest {
//        viewModel.onAction(
//            LoadWalletScreenAction.OnProceedClicked(walletDetail = fakeWalletDetails)
//        )
//        advanceUntilIdle()
//
//        val state = viewModel.state.value
//        state.walletIdError shouldBe SharedRes.Strings.required
//        state.amountError shouldBe SharedRes.Strings.required
//        state.remarksError shouldBe SharedRes.Strings.required
//    }
//
//    @Test
//    fun validate_success_should_emit_confirmation_effect() = runTest {
//        val fakeWalletValidationResponse = FakeWalletResponses.fakeWalletVerificationResponse
//
//        everySuspend {
//            validateWalletUseCase(fakeWalletDetails.id.toString(), "9840173991", "Test_remarks")
//        } returns ApiResult.Success(configMockWalletValidationDetail())
//
////        everySuspend {
////            walletServiceChargeUseCase("100", "user123", "SERVICE")
////        } returns ApiResult.Success(
////
////        )
//
//
//    }
//
//
//    fun configMockWalletValidationDetail() = WalletValidationDetail(
//        message = "Please confirm your details and proceed",
//        status = "success",
//        customerName = null,
//        customerProfileImageUrl = null,
//        validationIdentifier = null
//    )
//
//
//}