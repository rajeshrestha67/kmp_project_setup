package org.rajesh.mobile_banking.login.data.repository

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.login.data.dto.LoginRequestDTO
import dev.rajesh.mobile_banking.login.data.repository.UserRepositoryImpl
import dev.rajesh.mobile_banking.login.domain.repository.UserRepository
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.toByteArray
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json

import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserRepositoryImplTest : KoinTest {

    private val repository: UserRepository by inject()


    @BeforeTest
    fun setUp() {
        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath.endsWith(EndPoint.LOGIN)) {
                val bodyText = request.body.toByteArray().decodeToString()
                val loginRequest = Json.decodeFromString<LoginRequestDTO>(bodyText)
                when {
                    loginRequest.username != "valid@gmail.com" -> {
                        respond(
                            content = """{"message": "invalid email"}""",
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }

                    loginRequest.password != "password" -> {
                        respond(
                            content = """{"message": "invalid password"}""",
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }

                    else -> {
                        respond(
                            content = """{"token": "fakeToken","role": "HRM_EMPLOYEE"}""",
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                }

            } else {
                respondBadRequest()
            }
        }

        startKoin {
            modules(
                module {
                    single {
                        HttpClient(mockEngine) {
                            install(ContentNegotiation) {
                                json(
                                    Json {
                                        ignoreUnknownKeys = true
                                        isLenient = true
                                    }
                                )
                            }
                        }
                    }
                    singleOf(::UserRepositoryImpl).bind<UserRepository>()
                    singleOf(::FakeTokenRepository).bind<TokenRepository>()
                }
            )
        }

    }

    @Test
    fun `login - success return success-style ApiResult`() = runTest {

    }
}