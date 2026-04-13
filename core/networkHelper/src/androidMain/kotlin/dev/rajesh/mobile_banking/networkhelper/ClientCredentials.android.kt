package dev.rajesh.mobile_banking.networkhelper

import dev.rajesh.mobile_banking.oauthnative.OAuthNativeBridge

actual object ClientCredentials {
    actual val clientId: String get() = OAuthNativeBridge.clientId()
    actual val clientSecret: String get() = OAuthNativeBridge.clientSecret()
}
