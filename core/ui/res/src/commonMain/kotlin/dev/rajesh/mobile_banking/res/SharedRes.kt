package dev.rajesh.mobile_banking.res

import mobilebanking.core.ui.res.generated.resources.Poppins_Bold
import mobilebanking.core.ui.res.generated.resources.Poppins_Italic
import mobilebanking.core.ui.res.generated.resources.Poppins_Medium
import mobilebanking.core.ui.res.generated.resources.Res
import mobilebanking.core.ui.res.generated.resources.*

object SharedRes {
    fun getRes(path: String): String {
        return Res.getUri(path)
    }

    object Fonts {
        val poppinsMedium = Res.font.Poppins_Medium
        val poppinsBold = Res.font.Poppins_Bold
        val poppinsItalic = Res.font.Poppins_Italic
    }

    object Icons {
        val logout = Res.drawable.outline_logout_24
        val ic_notification = Res.drawable.ic_notification
        val ic_qr_scanner = Res.drawable.ic_qr_scanner
    }

    object Strings {
        val welcome = Res.string.welcome
        val email = Res.string.email
        val userName = Res.string.username
        val mobileNumber = Res.string.mobileNumber
        val password = Res.string.password
        val login = Res.string.login
        val enterYourMobileNumber = Res.string.enter_your_mobile_number
        val invalidUsername = Res.string.invalid_username
        val invalidEmailAddress = Res.string.invalid_email_address
        val invalidMobileNumber = Res.string.invalid_mobile_number
        val enterYourPassword = Res.string.enter_your_password
        val required = Res.string.required
        val next = Res.string.next
        val skip = Res.string.skip
        val getStarted = Res.string.getStarted

        val english = Res.string.english
        val nepali = Res.string.nepali
        val light = Res.string.light
        val dark = Res.string.dark
        val system = Res.string.system

        val invalidPasswordUppercase = Res.string.invalid_password_uppercase
        val invalidPasswordLowercase = Res.string.invalid_password_lowercase
        val invalidPasswordDigit = Res.string.invalid_password_digit
        val invalidPasswordLength = Res.string.invalid_password_length
        val invalidPasswordSpecialChar = Res.string.invalid_password_special_char
        val home = Res.string.home
        val banking = Res.string.banking
        val transactionHistory = Res.string.transactionHistory
        val menu = Res.string.menu
        val goodMorning = Res.string.good_morning
        val goodAfternoon = Res.string.good_afternoon
        val goodEvening = Res.string.good_evening
        val goodNight = Res.string.good_night
        val a_c_no = Res.string.a_c_no
        val actualBalance = Res.string.actualBalance
        val availableBalance = Res.string.availableBalance
        val currencyType = Res.string.currencyType
        val sameBankTransfer = Res.string.sameBankTransfer
        val otherBankTransfer  = Res.string.otherBankTransfer
        val bankTransfer = Res.string.bankTransfer
        val favouriteAccounts = Res.string.favouriteAccounts
        val sameBankTransferDesc = Res.string.sameBankTransferDesc
        val otherBankTransferDesc = Res.string.otherBankTransferDesc
        val favouriteAccountDesc = Res.string.favouriteAccountDesc
        val fullName = Res.string.fullName
        val accountNumber = Res.string.account_number
        val branch = Res.string.branch
        val amount = Res.string.amount
        val remarks = Res.string.remarks
        val proceed = Res.string.proceed
        val chooseAccount = Res.string.chooseAccount
        val primary = Res.string.primary




    }
}