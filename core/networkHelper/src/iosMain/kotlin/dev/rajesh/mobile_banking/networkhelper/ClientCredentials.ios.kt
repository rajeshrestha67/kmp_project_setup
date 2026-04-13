package dev.rajesh.mobile_banking.networkhelper

import platform.Foundation.NSBundle
import platform.Foundation.NSString

private object OAuthKeychainBootstrap {
    private var didRun: Boolean = false

    private fun ensureSeededFromInfoPlist() {
        if (didRun) return
        didRun = true
        if (OAuthKeychainStorage.hasClientCredentials()) return
        val dict = NSBundle.mainBundle.infoDictionary ?: return
        val rawId = dict.objectForKey("MBK_CLIENT_ID") ?: return
        val rawSecret = dict.objectForKey("MBK_CLIENT_SECRET") ?: return
        val clientId = (rawId as? NSString)?.description ?: rawId.toString()
        val clientSecret = (rawSecret as? NSString)?.description ?: rawSecret.toString()
        if (clientId.isBlank() || clientSecret.isBlank()) return
        OAuthKeychainStorage.store("client_id", clientId)
        OAuthKeychainStorage.store("client_secret", clientSecret)
    }

    fun clientId(): String {
        ensureSeededFromInfoPlist()
        return OAuthKeychainStorage.read("client_id")
            ?: error("Missing OAuth client id: set MBK_CLIENT_ID / MBK_CLIENT_SECRET in Info.plist (seeded into Keychain on first launch).")
    }

    fun clientSecret(): String {
        ensureSeededFromInfoPlist()
        return OAuthKeychainStorage.read("client_secret")
            ?: error("Missing OAuth client secret: set MBK_CLIENT_SECRET in Info.plist.")
    }
}

actual object ClientCredentials {
    actual val clientId: String get() = OAuthKeychainBootstrap.clientId()
    actual val clientSecret: String get() = OAuthKeychainBootstrap.clientSecret()
}
