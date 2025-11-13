package dev.rajesh.mobile_banking.di

import dev.rajesh.datastore.DataStoreFactory
import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.datastore.token.repository.TokenRepositoryImpl
import org.koin.core.annotation.Factory
import org.koin.mp.KoinPlatform.getKoin
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataStoreModule {

    @Single
    fun getTokenDataStore(): TokenDataStore {
        val factory: DataStoreFactory = getKoin().get()
        return factory.getTokenDataStore("token")
    }

    @Factory(binds = [TokenRepository::class])
    fun getTokenRepository(tokenDataStore: TokenDataStore) = TokenRepositoryImpl(tokenDataStore)

}