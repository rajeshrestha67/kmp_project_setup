package dev.rajesh.mobile_banking.otpverification.presentation.state

sealed class OtpEffect {
    object NeedsVerification : OtpEffect()     // Navigate: Parent -> OTP
    object ActionSuccess : OtpEffect()        // Navigate: Parent -> Final (Directly)
    object VerificationSuccess : OtpEffect()  // Navigate: OTP -> Final
    data class OtpError(val message: String) : OtpEffect() // Stay on OTP + Error
}