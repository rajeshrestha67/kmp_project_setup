package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.data.auth.login.UserRepositoryImpl
import dev.rajesh.mobile_banking.domain.auth.login.repository.UserRepository
import dev.rajesh.mobile_banking.domain.auth.login.usecase.LoginUseCase
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.domain.form.UsernameValidateUseCase
import dev.rajesh.mobile_banking.login.LoginViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class AuthModule {

    @Factory(binds = [UserRepository::class])
    fun userRepository(httpClient: HttpClient) = UserRepositoryImpl(httpClient)


    @Factory
    fun loginUseCase(
        userRepository: UserRepository
    ) = LoginUseCase(
        userRepository = userRepository
    )

    @KoinViewModel
    fun loginViewModel(
        loginUseCase: LoginUseCase,
        mobileNumberValidateUseCase: MobileNumberValidateUseCase,
        passwordValidateUseCase: PasswordValidateUseCase
    ) = LoginViewModel(
        loginUseCase = loginUseCase,
        mobileNumberValidateUseCase = mobileNumberValidateUseCase,
        passwordValidateUseCase = passwordValidateUseCase
    )
}