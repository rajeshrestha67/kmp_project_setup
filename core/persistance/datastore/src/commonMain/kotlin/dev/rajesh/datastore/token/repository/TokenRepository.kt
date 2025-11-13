package dev.rajesh.datastore.token.repository

import dev.rajesh.datastore.token.model.Token
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    val token: Flow<Token>

    suspend fun saveToken(token: Token)

}