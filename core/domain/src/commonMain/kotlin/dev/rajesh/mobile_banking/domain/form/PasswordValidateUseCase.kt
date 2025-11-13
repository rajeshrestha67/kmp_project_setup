package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.validate
import org.jetbrains.compose.resources.StringResource

class PasswordValidateUseCase {

    operator fun invoke(password: String): StringResource? {
        return FormValidate.passwordValidationRules.validate(password)
    }
}