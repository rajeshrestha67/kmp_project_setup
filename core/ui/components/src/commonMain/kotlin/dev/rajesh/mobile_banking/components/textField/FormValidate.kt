package dev.rajesh.mobile_banking.components.textField

import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.StringResource

object FormValidate {
    fun validateRules(text: String?, rules: List<Rule>): StringResource? {
        rules.map { rule ->
            val errorMessage = rule.check.invoke(text ?: "")
            if (errorMessage != null) {
                return@validateRules errorMessage
            }
        }
        return null
    }

    val requiredRule = Rule { text ->
        if (text.isEmpty()) {
            SharedRes.Strings.required
        } else {
            null
        }
    }


    val emailRegex =
        Regex("^[a-zA-Z0-9.!#\$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*\$")
    val emailRegex2 = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$")

    val emailRule = Rule { text ->
        if (!text.matches(emailRegex2)) {
            SharedRes.Strings.invalidEmailAddress
        } else {
            null
        }
    }

    val usernameRule = Rule { text ->
        if (!text.matches(Regex(".{10,}"))) {
            SharedRes.Strings.invalidUsername
        } else {
            null
        }

    }

    val mobileNumberRule = Rule { text ->
        val nepaliMobilePattern = Regex("^(98|97)\\d{8}$")
        if (!text.matches(nepaliMobilePattern)) {
            //SharedRes.Strings.invalidUsername
            SharedRes.Strings.invalidMobileNumber
        } else {
            null
        }
    }

    val upperCaseRule = Rule { text ->
        if (!text.matches(Regex(".*[A-Z].*"))) {
            SharedRes.Strings.invalidPasswordUppercase
        } else {
            null
        }
    }

    val lowerCaseRule = Rule { text ->
        if (!text.matches(Regex(".*[a-z].*"))) {
            SharedRes.Strings.invalidPasswordLowercase
        } else {
            null
        }
    }

    val digitRule = Rule { text ->
        if (!text.matches(Regex(".*[0-9].*"))) {
            SharedRes.Strings.invalidPasswordDigit
        } else {
            null
        }
    }
    val specialCharRule = Rule { text ->
        if (!text.matches(Regex(".*[^A-Za-z0-9].*"))) {
            SharedRes.Strings.invalidPasswordSpecialChar
        } else {
            null
        }
    }
    val passwordLengthRule = Rule { text ->
        // if (!text.matches(Regex(".{6,}"))) {  //for 6 digit password
        if (!text.matches(Regex("^\\d{4,6}$"))) { //for 4-6 digit password
            SharedRes.Strings.invalidPasswordLength
        } else {
            null
        }
    }

    val requiredValidationRules = listOf(requiredRule)
    val emailValidationRules = requiredValidationRules + listOf(emailRule)

    val passwordValidationRules = requiredValidationRules + listOf(
        //        upperCaseRule,
        //        lowerCaseRule,
        //        digitRule,
        //        specialCharRule,
        passwordLengthRule
    )

    val usernameValidationRules = requiredValidationRules + listOf(usernameRule)
    val mobileNumberValidationRules = requiredValidationRules + listOf(mobileNumberRule)
}

fun List<Rule>.validate(text: String?): StringResource? {
    return FormValidate.validateRules(text = text, this)
}