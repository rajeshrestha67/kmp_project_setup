package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.validate
import org.jetbrains.compose.resources.StringResource

class MobileNumberValidateUseCase {

    operator fun invoke(mobileNumber: String): StringResource? {
        val trimmed = mobileNumber.trim()
            .replace(" ", "")
            .replace("-", "")

        return FormValidate.mobileNumberValidationRules.validate(trimmed)

    }
}