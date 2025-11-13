package dev.rajesh.datastore.token.repository

import dev.rajesh.datastore.token.local.TokenDataStore
import dev.rajesh.datastore.token.model.Token
import kotlinx.coroutines.flow.Flow

class TokenRepositoryImpl(
    private val tokenDataStore: TokenDataStore
) : TokenRepository{
    override val token: Flow<Token> = tokenDataStore.tokenFlow

    override suspend fun saveToken(token: Token) {
        tokenDataStore.update(token = token)
    }

}