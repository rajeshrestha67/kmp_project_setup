package dev.rajesh.mobile_banking.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.rajesh.datastore.DataStoreFactory
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.download.platform.IFileDownloader
import dev.rajesh.mobile_banking.download.platform.IosFileDownloader
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoder
import dev.rajesh.mobile_banking.qrscanner.domain.qrDecoder.QrDecoderFactory
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.mp.KoinPlatform

@Module
actual class PlatformModule {
    @Single
    actual fun getPlatformMessage(): PlatformMessage {
        return PlatformMessage()
    }

    @Factory
    actual fun provideQrDecoderFactory(): QrDecoderFactory =
        QrDecoderFactory()

    @Factory
    actual fun provideQrDecoder(factory: QrDecoderFactory): QrDecoder =
        factory.create()

    @Single
    actual fun provideFileDownloader(): IFileDownloader = IosFileDownloader()

    @Single
    actual fun getDataStoreFactory(): DataStoreFactory = DataStoreFactory()

    @Single
    actual fun provideDataStore(factory: DataStoreFactory): DataStore<Preferences> {
        return factory.createDataStore()
    }
}