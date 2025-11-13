package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.crypto.Cryptography
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
expect class CryptographyModule() {

    @Single
    fun getCryptography(): Cryptography
}