package org.rajesh.mobile_banking.login.fake

import dev.rajesh.datastore.token.model.Token
import dev.rajesh.datastore.token.repository.TokenRepository
import kotlinx.coroutines.flow.flowOf

class FakeTokenRepository(
    /*override val token: Flow<Token>*/
) : TokenRepository {
    var savedToken: Token? = null

    override suspend fun saveToken(token: Token) {
        savedToken = token
    }

    override val token = flowOf(Token())

}