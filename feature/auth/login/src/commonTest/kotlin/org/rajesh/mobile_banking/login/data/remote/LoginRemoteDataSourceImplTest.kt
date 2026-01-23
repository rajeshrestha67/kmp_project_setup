package org.rajesh.mobile_banking.login.data.remote

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSource
import dev.rajesh.mobile_banking.login.data.datasource.LoginRemoteDataSourceImpl
import dev.rajesh.mobile_banking.model.network.toErrorMessage
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import io.kotest.engine.runBlocking
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondBadRequest
import io.ktor.client.engine.mock.toByteArray
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.toMap
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.rajesh.mobile_banking.login.fake.FakeTokenRepository
import org.rajesh.mobile_banking.login.testUtils.fakeLoginRequest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class LoginRemoteDataSourceImplTest : KoinTest {

    private val remoteDataSource: LoginRemoteDataSource by inject()

    @BeforeTest
    fun setUp() = runTest {
        val mockEngine = MockEngine { request ->
            if (request.url.encodedPath.endsWith(EndPoint.LOGIN)) {
                val bodyText = request.body.toByteArray().decodeToString()
                //val loginRequest = Json.decodeFromString<LoginRequestDTO>(bodyText)

                val formData = (request.body as FormDataContent).formData.toMap()

                val username = formData["username"]?.first().orEmpty()
                val password = formData["password"]?.first().orEmpty()
                when {
                    username != "9802304437" -> {
                        respond(
                            content = """{"error":"error",
                                "error_description": "invalid username"}""".trimMargin(),
                            status = HttpStatusCode.BadRequest,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }

                    password != "2287" -> {
                        respond(
                            content = """{"error":"error",
                                "error_description": "Bad Credentials"}""".trimMargin(),
                            status = HttpStatusCode.BadRequest,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }

                    else -> {
                        respond(
                            content = """
                            {
                                "access_token": "fake_token",
                                "expires_in" : 123,
                                "refresh_token" : "fake_refresh_token",
                                "scope" : "fake_scope",
                                "token_type" : "fake_token_type"
                            }""".trimIndent(),
                            headers = headersOf(
                                HttpHeaders.ContentType,
                                "application/json"
                            )
                        )
                    }
                }
            } else {
                respondBadRequest()
            }
        }

        startKoin {
            modules(
                modules = module {
                    single {
                        HttpClient(mockEngine) {
                            install(ContentNegotiation) {
                                json(Json {
                                    ignoreUnknownKeys = true
                                    isLenient = true
                                })
                            }
                        }
                    }
                    singleOf(::LoginRemoteDataSourceImpl).bind<LoginRemoteDataSource>()
                    singleOf(::FakeTokenRepository).bind<TokenRepository>()
                }
            )
        }

    }

    @Test
    fun login_returns_success_api_result() = runTest {
        val result = remoteDataSource.login(fakeLoginRequest("9802304437", "2287"))
        when (result) {
            is ApiResult.Success -> {
                result.data.access_token shouldBe "fake_token"
            }

            else -> error("Expected success")
        }
    }

    @Test
    fun login_returns_invalid_username() = runTest {
        val result = remoteDataSource.login(fakeLoginRequest("9802304438", "2287"))
        when (result) {
            is ApiResult.Error -> result.error.toErrorMessage() shouldBe "invalid username"
            else -> error("Expected error")
        }
    }

    @Test
    fun login_returns_empty_username() = runTest {
        val result = remoteDataSource.login(fakeLoginRequest("", "2287"))
        when (result) {
            is ApiResult.Error -> result.error.toErrorMessage() shouldBe "invalid username"
            else -> error("Expected error")
        }
    }

    @Test
    fun login_returns_invalid_password() = runTest {
        val result = remoteDataSource.login(fakeLoginRequest("9802304437", "2222"))
        when (result) {
            is ApiResult.Error -> result.error.toErrorMessage() shouldBe "Bad Credentials"
            else -> error("Expected error")
        }
    }

    @Test
    fun login_returns_empty_password() = runTest {
        val result = remoteDataSource.login(fakeLoginRequest("9802304437", ""))
        when (result) {
            is ApiResult.Error -> result.error.toErrorMessage() shouldBe "Bad Credentials"
            else -> error("Expected error")
        }
    }

    @AfterTest
    fun tearDown() = runBlocking {
        stopKoin()
    }
}