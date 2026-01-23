package dev.rajesh.mobile_banking.di

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSource
import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSourceImpl
import dev.rajesh.mobile_banking.login.data.repository.UserRepositoryImpl
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.login.domain.usecase.LoginUseCase
import dev.rajesh.mobile_banking.login.presentation.viewModel.LoginViewModel
import dev.rajesh.mobile_banking.otpverification.presentation.viewmodel.OtpVerificationViewModel
import io.ktor.client.HttpClient
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class AuthModule {


    @Factory(binds = [LoginRemoteDataSource::class])
    fun provideLoginRemoteDataSource(
        httpClient: HttpClient
    ) = LoginRemoteDataSourceImpl(
        httpClient = httpClient
    )

    @Factory(binds = [UserRepository::class])
    fun userRepository(
        loginRemoteDataSource: LoginRemoteDataSource
    ) = UserRepositoryImpl(remoteDataSource = loginRemoteDataSource)

    @Factory
    fun loginUseCase(
        userRepository: UserRepository,
        tokenRepository: TokenRepository
    ) = LoginUseCase(
        userRepository = userRepository,
        tokenRepository = tokenRepository,
    )

    @KoinViewModel
    fun loginViewModel(
        loginUseCase: LoginUseCase,
        mobileNumberValidateUseCase: MobileNumberValidateUseCase,
        passwordValidateUseCase: PasswordValidateUseCase,
    ) = LoginViewModel(
        loginUseCase = loginUseCase,
        mobileNumberValidateUseCase = mobileNumberValidateUseCase,
        passwordValidateUseCase = passwordValidateUseCase,
    )
}