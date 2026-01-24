package org.rajesh.mobile_banking.login.presentation.viewModel

import dev.rajesh.mobile_banking.components.device_info.DeviceInfoProvider
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.logger.AppLogger
import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.login.presentation.state.LoginScreenAction
import dev.rajesh.mobile_banking.login.presentation.viewModel.LoginViewModel
import dev.rajesh.mobile_banking.res.SharedRes
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.rajesh.mobile_banking.login.fake.FakeDeviceInfoProvider
import org.rajesh.mobile_banking.login.fake.FakeTokenRepository
import org.rajesh.mobile_banking.login.fake.FakeUserRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

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
        startKoin {  }
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
    fun invalid_mobile_number_should_update_mobile_number_error_and_not_call_login ()= runTest {
        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("123"))
        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("1234"))
        loginViewModel.onAction(LoginScreenAction.LoginClicked)

        advanceUntilIdle()
        val state = loginViewModel.state.value
        AppLogger.i("LoginViewModelTest", "error: ${state.mobileNumberError}")
        state.mobileNumberError shouldBe SharedRes.Strings.invalidMobileNumber
    }

//    @Test
//    fun invalid_password_should_update_passwordError_and_not_call_login ()= runTest {
//        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9802304437"))
//        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("12"))
//        loginViewModel.onAction(LoginScreenAction.LoginClicked)
//
//        val state = loginViewModel.state.value
//        state.passwordError shouldBe SharedRes.Strings.invalidPassword
//    }

//    @Test
//    fun on_mobile_number_changed_updates_state() = runTest {
//        loginViewModel.onAction(LoginScreenAction.OnMobileNumberChanged("9800000000"))
//        loginViewModel.state.value.mobileNumber shouldBe "9800000000"
//    }

//    @Test
//    fun on_password_changed_updates_state() = runTest {
//        loginViewModel.onAction(LoginScreenAction.OnPasswordChanged("my_secret"))
//        loginViewModel.state.value.password shouldBe "my_secret"
//    }


//    @Test
//    fun on_mobile_number_error_action_updates_state() = runTest {
//        loginViewModel.onAction(LoginScreenAction.OnMobileNumberError(SharedRes.Strings.invalidMobileNumber))
//        loginViewModel.state.value.mobileNumberError shouldBe SharedRes.Strings.invalidMobileNumber
//    }

//
//    @Test
//    fun on_password_error_action_updates_state() = runTest {
//        loginViewModel.onAction(LoginScreenAction.OnPasswordError(SharedRes.Strings.invalidPassword))
//        loginViewModel.state.value.passwordError shouldBe SharedRes.Strings.invalidPassword
//    }

}