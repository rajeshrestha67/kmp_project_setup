package org.rajesh.mobile_banking.login.presentation.viewModel

import dev.rajesh.mobile_banking.components.device_info.DeviceInfoProvider
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.login.presentation.state.LoginScreenAction
import dev.rajesh.mobile_banking.login.presentation.ui.LoginScreen
import dev.rajesh.mobile_banking.login.presentation.viewModel.LoginViewModel
import dev.rajesh.mobile_banking.otpverification.presentation.state.OtpEffect
import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.rajesh.mobile_banking.login.fake.FakeDeviceInfoProvider
import org.rajesh.mobile_banking.login.fake.FakeTokenRepository
import org.rajesh.mobile_banking.login.fake.FakeUserRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import io.kotest.matchers.types.shouldBeTypeOf
import kotlin.test.fail

class LoginViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeUserRepository: FakeUserRepository
    private lateinit var fakeTokenRepository: FakeTokenRepository
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginUseCase: LoginUseCase

    private val mobileValidator = MobileNumberValidateUseCase()
    private val passwordValidator = PasswordValidateUseCase()
    private val fakeDeviceProvider: DeviceInfoProvider = FakeDeviceInfoProvider()

    @OptIn(ExperimentalCoroutinesApi::class)
    @BeforeTest
    fun setUp() = runTest {
        startKoin { }
        Dispatchers.setMain(testDispatcher)
        fakeUserRepository = FakeUserRepository()
        fakeTokenRepository = FakeTokenRepository()

        loginUseCase = LoginUseCase(fakeUserRepository, fakeTokenRepository)

        loginViewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            mobileNumberValidateUseCase = mobileValidator,
            passwordValidateUseCase = passwordValidator,
            deviceInfoProvider = fakeDeviceProvider
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @AfterTest
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun invalid_mobile_number_should_update_mobile_number_error_and_not_call_login() = runTest {
        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("123"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("1234"))
        loginViewModel.onAction(LoginScreenAction.LoginClicked)

        advanceUntilIdle()
        val state = loginViewModel.state.value
        state.mobileNumberError shouldBe SharedRes.Strings.invalidMobileNumber
    }

    @Test
    fun invalid_password_should_update_passwordError_and_not_call_login() = runTest {
        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9802304437"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("12"))
        loginViewModel.onAction(LoginScreenAction.LoginClicked)

        val state = loginViewModel.state.value
        state.passwordError shouldBe SharedRes.Strings.invalidPasswordLength
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun valid_mobile_number_and_password_otp_not_required_emit_action_success() = runTest {
        val effects = mutableListOf<OtpEffect>()
        val job = launch { loginViewModel.otpEffect.collect { effects.add(it) } }

        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9802304437"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("1234"))

        loginViewModel.onAction(LoginScreenAction.LoginClicked)
        advanceTimeBy(500)
        loginViewModel.state.value.isLoading shouldBe true

        advanceUntilIdle()

        // Assert Final State
        loginViewModel.state.value.isLoading shouldBe false
        effects.last() shouldBe OtpEffect.ActionSuccess

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun valid_mobile_and_password_otp_required_emit_need_verification() = runTest {
        fakeUserRepository.shouldReturnUnauthorized = true

        val effects = mutableListOf<OtpEffect>()
        val job = launch { loginViewModel.otpEffect.collect { effects.add(it) } }

        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9802304437"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("1234"))

        loginViewModel.onAction(LoginScreenAction.LoginClicked)
        advanceTimeBy(500)
        loginViewModel.state.value.isLoading shouldBe true

        advanceUntilIdle()

        // Assert Final State
        loginViewModel.state.value.isLoading shouldBe false
        effects.last() shouldBe OtpEffect.NeedsVerification

        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun when_wrong_otp_is_provided_login_fails_and_emits_error() = runTest {
        fakeUserRepository.shouldShowWrongOtpError = true
        val effects = mutableListOf<OtpEffect>()
        val job = launch { loginViewModel.otpEffect.collect { effects.add(it) } }

        loginViewModel.login(otp = "123456")
        advanceUntilIdle()

        val lastEffect = effects.lastOrNull()

        if (lastEffect is OtpEffect.OtpError) {
            lastEffect.message.shouldNotBeBlank()
        } else {
            fail("Expected OtpError but got $lastEffect")
        }
        loginViewModel.state.value.isLoading shouldBe false
        job.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun login_fails_without_OTP_emit_error_in_in_error_channel() = runTest {
        fakeUserRepository.shouldShowError = true
        val errorMessages = mutableListOf<String>()

        val job = launch { loginViewModel.errorChannel.collect { errorMessages.add(it) } }

        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9802304437"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("1234"))
        loginViewModel.onAction(LoginScreenAction.LoginClicked)

        advanceUntilIdle()

        errorMessages.size shouldBe 1
        errorMessages.first().shouldNotBeBlank()
        loginViewModel.state.value.isLoading shouldBe false

        job.cancel()
    }

    @Test
    fun when_OnMobileNumberError_action_is_received_state_should_reflect_the_error ()= runTest {
        // Arrange
        val expectedError = "Invalid mobile number format"
        val error = SharedRes.Strings.invalidMobileNumber

        // Act
        loginViewModel.onAction(LoginScreenAction.OnMobileNumberError(error))

        // Assert
        loginViewModel.state.value.mobileNumberError shouldBe error
    }

    @Test
    fun when_OnPasswordError_action_is_received_state_should_reflect_the_error() {
        // Arrange
        val expectedError = SharedRes.Strings.invalidPasswordDigit

        // Act
        loginViewModel.onAction(LoginScreenAction.OnPasswordError(expectedError))

        // Assert
        loginViewModel.state.value.passwordError shouldBe expectedError
    }

}