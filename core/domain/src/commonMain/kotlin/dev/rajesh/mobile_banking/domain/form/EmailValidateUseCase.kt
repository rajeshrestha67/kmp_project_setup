package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.validate
import org.jetbrains.compose.resources.StringResource

class EmailValidateUseCase {

    operator fun invoke(email:String): StringResource?{
        return FormValidate.emailValidationRules.validate(email)
    }
}