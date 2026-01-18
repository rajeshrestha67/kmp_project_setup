package dev.rajesh.mobile_banking.di

import android.content.Context
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoderFactory
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Module
actual class PlatformModule actual constructor() : KoinComponent {

    private val context: Context by inject()    // Fetch context from Koin's androidContext()

    @Single
    actual fun getPlatformMessage(): PlatformMessage {
        return PlatformMessage()
    }

    @Factory
    actual fun provideQrDecoderFactory(): QrDecoderFactory =
        QrDecoderFactory(context)

    @Factory
    actual fun provideQrDecoder(factory: QrDecoderFactory): QrDecoder =
        factory.create()

}