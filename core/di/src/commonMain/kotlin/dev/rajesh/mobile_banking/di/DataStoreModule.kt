package dev.rajesh.mobile_banking.di

import dev.rajesh.datastore.DataStoreFactory
import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.datastore.token.repository.TokenRepositoryImpl
import dev.rajesh.datastore.userData.datastore.UserDetailDataStore
import dev.rajesh.datastore.userData.repository.UserDetailLocalDataSource
import dev.rajesh.datastore.userData.repository.UserDetailLocalDataSourceImpl
import org.koin.core.annotation.Factory
import org.koin.mp.KoinPlatform.getKoin
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DataStoreModule {

    @Single
    fun getDataStoreFactory(): DataStoreFactory = DataStoreFactory()

    //token

    @Single
    fun getTokenDataStore(): TokenDataStore {
        val factory: DataStoreFactory = getKoin().get()
        return factory.getTokenDataStore("token")
    }

    @Factory(binds = [TokenRepository::class])
    fun getTokenRepository(tokenDataStore: TokenDataStore) = TokenRepositoryImpl(tokenDataStore)


    //UserDetails

    @Single
    fun getUserDetailDS(): UserDetailDataStore {
        val factory: DataStoreFactory = getKoin().get()
        return factory.getUserDetailsDS("user_details")
    }

    @Factory(binds = [UserDetailLocalDataSource::class])
    fun getUserDetailLocalDS(userDetailDataStore: UserDetailDataStore) = UserDetailLocalDataSourceImpl(userDetailDataStore)
}