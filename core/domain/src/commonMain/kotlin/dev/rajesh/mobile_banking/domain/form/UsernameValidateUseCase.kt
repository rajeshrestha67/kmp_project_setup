package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.validate
import org.jetbrains.compose.resources.StringResource

class UsernameValidateUseCase {

    operator fun invoke(username: String): StringResource? {
        return FormValidate.usernameValidationRules.validate(username)
    }

}