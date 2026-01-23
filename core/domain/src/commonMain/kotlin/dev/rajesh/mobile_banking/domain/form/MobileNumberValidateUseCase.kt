package dev.rajesh.mobile_banking.domain.form

import dev.rajesh.mobile_banking.components.textField.FormValidate
import dev.rajesh.mobile_banking.components.textField.validate
import org.jetbrains.compose.resources.StringResource

class MobileNumberValidateUseCase {

    operator fun invoke(mobileNumber: String): StringResource? {
        var trimmed = mobileNumber.trim()
            .replace(" ", "")
            .replace("-", "")
            .replace("+", "")
            .replace("(", "")
            .replace(")", "")

        if (trimmed.startsWith("977")) {
            trimmed = trimmed.replaceFirst("977", "")
        }
        return FormValidate.mobileNumberValidationRules.validate(trimmed)
    }
}