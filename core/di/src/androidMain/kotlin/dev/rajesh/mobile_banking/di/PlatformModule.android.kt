package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.components.PlatformMessage
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule actual constructor() {
    @Single
    actual fun getPlatformMessage(): PlatformMessage {
        return PlatformMessage()
    }

}