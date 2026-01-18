package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoderFactory
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
actual class PlatformModule {
    @Single
    actual fun getPlatformMessage(): PlatformMessage {
        return PlatformMessage()
    }

    @Factory
    actual  fun provideQrDecoderFactory(): QrDecoderFactory =
        QrDecoderFactory()

    @Factory
    actual fun provideQrDecoder(factory: QrDecoderFactory): QrDecoder =
        factory.create()
}