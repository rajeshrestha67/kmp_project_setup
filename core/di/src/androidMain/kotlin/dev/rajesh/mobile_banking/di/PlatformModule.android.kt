package dev.rajesh.mobile_banking.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import dev.rajesh.datastore.DataStoreFactory
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.database.configs.AppDatabase
import dev.rajesh.mobile_banking.database.configs.DatabaseFactory
import dev.rajesh.mobile_banking.download.platform.AndroidIFileDownloader
import dev.rajesh.mobile_banking.download.platform.IFileDownloader
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


    @Single(binds = [IFileDownloader::class])
    actual fun provideFileDownloader(): IFileDownloader = AndroidIFileDownloader(context)

    @Single
    actual fun getDataStoreFactory(): DataStoreFactory = DataStoreFactory()

    @Single
    actual fun provideDataStore(factory: DataStoreFactory): DataStore<Preferences> {
        return factory.createDataStore()
    }

    @Single
    actual fun provideDatabaseBuilder(): DatabaseFactory = DatabaseFactory(context)

    @Single
    actual fun provideDatabase(factory: DatabaseFactory): AppDatabase =
        factory.createDatabaseBuilder().build()


}