package dev.rajesh.mobile_banking.di

import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoderFactory
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
expect class PlatformModule() {

    @Single
    fun getPlatformMessage(): PlatformMessage

    @Factory
    fun provideQrDecoderFactory(): QrDecoderFactory

    @Factory
    fun provideQrDecoder(factory: QrDecoderFactory): QrDecoder

}