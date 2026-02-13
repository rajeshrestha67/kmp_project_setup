package dev.rajesh.mobile_banking.home.data.remote.dataSource

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSourceImpl
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.testUtils.fakes.FakeTokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class BankingServiceRemoteDataSourceImplTest : KoinTest {

    private lateinit var mockEngine: MockEngine
    private val dataSource: BankingServiceRemoteDataSource by inject()


    @BeforeTest
    fun setUp() {
        mockEngine = MockEngine { //request ->
            respond(
                content = bankingServiceSuccessResponseJson,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    "application/json"
                )
            )
        }

        startKoin {
            modules(module {
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

//                single<BankingServiceRemoteDataSource> {
//                    BankingServiceRemoteDataSourceImpl(get())
//                }
                singleOf(::BankingServiceRemoteDataSourceImpl).bind<BankingServiceRemoteDataSource>()
                singleOf(::FakeTokenRepository).bind<TokenRepository>()

            })
        }

    }


    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchBankingService_returns_success_when_api_responds_ok() = runTest {
        val result = dataSource.fetchBankingService()
        require(result is ApiResult.Success)
        assertTrue(result.data.details?.isNotEmpty() ?: false)
    }

    @Test
    fun fetchBankingService_returns_error_on_failure() = runTest {
        mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val errorDataSource = BankingServiceRemoteDataSourceImpl(client)
        val result = errorDataSource.fetchBankingService()

        assertTrue(result is ApiResult.Error)
    }


    companion object {
        private val bankingServiceSuccessResponseJson = """
            {
                "status": "Success",
                "code": "M0000",
                "message": "Details Fetched Successfully",
                "details": [
                {
                  "name": "Bank Transfer",
                  "uniqueIdentifier": "bank_transfer",
                  "type": "dashboard",
                  "status": "Active",
                  "imageUrl": "/mbank/serviceIcon/1619083189078264233cf-de5f-4235-8d0a-27d332e381f5.png",
                  "appOrder": 2,
                  "new": false
                },
                {
                  "name": "Load Wallet",
                  "uniqueIdentifier": "load_wallet",
                  "type": "dashboard",
                  "status": "Active",
                  "imageUrl": "/mbank/serviceIcon/1619081575273387590c0-73c9-476c-9363-66d73f723d02.png",
                  "appOrder": 3,
                  "new": false
                }
                ],
                "detail": null,
                "packages": null
            }""".trimIndent()
    }
}