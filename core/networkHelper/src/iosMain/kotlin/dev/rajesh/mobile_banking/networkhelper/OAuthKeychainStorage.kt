package dev.rajesh.mobile_banking.networkhelper

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value
import platform.CoreFoundation.CFBridgingRelease
import platform.CoreFoundation.CFTypeRefVar
import platform.CoreFoundation.kCFBooleanTrue
import platform.Foundation.NSData
import platform.Foundation.NSMutableDictionary
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import platform.Security.SecItemAdd
import platform.Security.SecItemCopyMatching
import platform.Security.SecItemDelete
import platform.Security.errSecSuccess
import platform.Security.kSecAttrAccount
import platform.Security.kSecAttrService
import platform.Security.kSecClass
import platform.Security.kSecClassGenericPassword
import platform.Security.kSecMatchLimit
import platform.Security.kSecMatchLimitOne
import platform.Security.kSecReturnData
import platform.Security.kSecValueData

private const val SERVICE = "dev.rajesh.mobile_banking.oauth"

@OptIn(ExperimentalForeignApi::class)
internal object OAuthKeychainStorage {

    fun hasClientCredentials(): Boolean =
        read("client_id") != null && read("client_secret") != null

    fun store(account: String, value: String) {
        val data = (value as NSString).dataUsingEncoding(NSUTF8StringEncoding) ?: return
        delete(account)
        val query = baseQuery(account)
        query.setObject(data, kSecValueData)
        SecItemAdd(query, null)
    }

    fun read(account: String): String? = memScoped {
        val query = baseQuery(account)
        query.setObject(kSecMatchLimitOne, kSecMatchLimit)
        query.setObject(kCFBooleanTrue, kSecReturnData)
        val result = alloc<CFTypeRefVar>()
        val status = SecItemCopyMatching(query, result.ptr)
        if (status != errSecSuccess) return@memScoped null
        val data = CFBridgingRelease(result.value) as? NSData ?: return@memScoped null
        return@memScoped NSString.create(data, NSUTF8StringEncoding)?.toString()
    }

    fun delete(account: String) {
        SecItemDelete(baseQuery(account))
    }

    private fun baseQuery(account: String): NSMutableDictionary {
        val d = NSMutableDictionary()
        d.setObject(kSecClassGenericPassword, kSecClass)
        d.setObject(SERVICE as NSString, kSecAttrService)
        d.setObject(account as NSString, kSecAttrAccount)
        return d
    }
}
