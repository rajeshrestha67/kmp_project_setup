package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.crypto.Cryptography
import dev.rajesh.mobile_banking.crypto.getPlatformCryptography
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single


@Module
actual class CryptographyModule {
    @Single
    actual fun getCryptography(): Cryptography {
        return getPlatformCryptography()
    }
}