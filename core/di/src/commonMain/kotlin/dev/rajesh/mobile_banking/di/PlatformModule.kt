package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.components.PlatformMessage
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
expect class PlatformModule() {

    @Single
    fun getPlatformMessage(): PlatformMessage

}