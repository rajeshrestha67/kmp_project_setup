package dev.rajesh.mobile_banking.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.RoomDatabase
import dev.rajesh.datastore.DataStoreFactory
import dev.rajesh.mobile_banking.components.PlatformMessage
import dev.rajesh.mobile_banking.database.configs.AppDatabase
import dev.rajesh.mobile_banking.database.configs.DatabaseFactory
import dev.rajesh.mobile_banking.download.platform.IFileDownloader
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

    @Single
    fun provideFileDownloader(): IFileDownloader

    @Single
    fun getDataStoreFactory(): DataStoreFactory

    @Single
    fun provideDataStore(factory: DataStoreFactory): DataStore<Preferences>

    /**
     * database
     */
    @Single
    fun provideDatabaseBuilder(): DatabaseFactory

    @Single
    fun provideDatabase(factory: DatabaseFactory): AppDatabase
}