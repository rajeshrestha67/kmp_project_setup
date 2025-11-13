package dev.rajesh.mobile_banking.di

import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import dev.rajesh.mobile_banking.domain.form.EmailValidateUseCase
import dev.rajesh.mobile_banking.domain.form.MobileNumberValidateUseCase
import dev.rajesh.mobile_banking.domain.form.PasswordValidateUseCase
import dev.rajesh.mobile_banking.domain.form.RequiredValidationUseCase
import dev.rajesh.mobile_banking.domain.form.UsernameValidateUseCase

@Module
class FormModule {

    @Single
    fun emailValidateUseCase() = EmailValidateUseCase()

    @Single
    fun usernameValidateUseCase() = UsernameValidateUseCase()

    @Single
    fun mobileNumberValidateUseCase() = MobileNumberValidateUseCase()

    @Single
    fun passwordValidateUseCase() = PasswordValidateUseCase()

    @Single
    fun requiredValidation() = RequiredValidationUseCase()
}