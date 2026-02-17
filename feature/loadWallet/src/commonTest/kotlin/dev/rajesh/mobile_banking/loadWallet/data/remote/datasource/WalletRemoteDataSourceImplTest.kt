package dev.rajesh.mobile_banking.loadWallet.data.remote.datasource

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSource
import dev.rajesh.mobile_banking.loadWallet.data.remote.WalletRemoteDataSourceImpl
import dev.rajesh.mobile_banking.loadWallet.presentation.model.WalletLoadRequest
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.testUtils.fakes.FakeTokenRepository
import io.kotest.matchers.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
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

class WalletRemoteDataSourceImplTest : KoinTest {

    private lateinit var mockEngine: MockEngine
    private val dataSource: WalletRemoteDataSource by inject()

    @BeforeTest
    fun setUp() {
        //mockEngine
        mockEngine = MockEngine { request ->

            if (request.url.encodedPath.endsWith(EndPoint.WALLET_LIST)) {
                respond(
                    content = walletListJsonResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
                )
            } else if (request.url.encodedPath.endsWith(EndPoint.WALLET_VALIDATION)) {
                respond(
                    content = walletValidationJsonResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
                )
            } else if (request.url.encodedPath.endsWith(EndPoint.WALLET_SERVICE_CHARGE)) {
                respond(
                    content = serviceChargeJsonResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
                )
            } else { // load wallet
                respond(
                    content = walletLoadJsonResponse,
                    status = HttpStatusCode.OK,
                    headers = headersOf(
                        HttpHeaders.ContentType,
                        "application/json"
                    )
                )
            }
        }
        //startKoin

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
                singleOf(::WalletRemoteDataSourceImpl).bind<WalletRemoteDataSource>()
                singleOf(::FakeTokenRepository).bind<TokenRepository>()

            })
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun fetch_wallet_list_returns_success_when_api_responds_ok() = runTest {
        val result = dataSource.getWalletList()
        require(result is ApiResult.Success)
        result.data.details?.isNotEmpty() shouldBe true
    }

    @Test
    fun fetch_wallet_list_returns_error_on_failure() = runTest {
        mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val errorDataSource = WalletRemoteDataSourceImpl(client)
        val result = errorDataSource.getWalletList()

        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun wallet_validation_returns_success_when_api_responds_ok() = runTest {
        val result = dataSource.validateWallet(
            "walletId",
            "9840173991",
            "1000"
        )
        require(result is ApiResult.Success)
        result.data.detail.status shouldBe "success"
    }

    @Test
    fun wallet_validation_returns_error_on_failure() = runTest {
        mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val errorDataSource = WalletRemoteDataSourceImpl(client)
        val result = errorDataSource.validateWallet(
            "walletId",
            "9840173991",
            "1000"
        )
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun fetchServiceCharge_returns_success_on_api_responds_ok() = runTest {
        val result = dataSource.getWalletCharge(
            "1000",
            "walletId",
            "SERVICE"
        )
        require(result is ApiResult.Success)
        result.data.details shouldBe 10.0
    }

    @Test
    fun fetchServiceCharge_returns_error_on_failure() = runTest {
        mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val errorDataSource = WalletRemoteDataSourceImpl(client)
        val result = errorDataSource.getWalletCharge(
            "1000",
            "walletId",
            "SERVICE"
        )
        assertTrue(result is ApiResult.Error)
    }

    @Test
    fun loadWallet_returns_success_on_api_responds_ok() = runTest {
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )
        val result = dataSource.walletLoad(walletLoadRequest)
        require(result is ApiResult.Success)
        result.data.message shouldBe "Successfully transferred to wallet"
    }

    @Test
    fun loadWallet_returns_error_on_failure() = runTest {
        mockEngine = MockEngine {
            respondError(HttpStatusCode.InternalServerError)
        }
        val client = HttpClient(mockEngine)
        val errorDataSource = WalletRemoteDataSourceImpl(client)
        val walletLoadRequest = WalletLoadRequest(
            senderAccountNumber = "00155555",
            walletId = "9840173991",
            walletUsername = "9840173991",
            amount = "1111",
            remarks = "test remarks",
            validationIdentifier = "123456"
        )
        val result = errorDataSource.walletLoad(walletLoadRequest)
        assertTrue(result is ApiResult.Error)
    }


    companion object {
        private val walletListJsonResponse = """
            {
              "status": "SUCCESS",
              "code": "M0000",
              "message": "Wallet Retrieved Successfully",
              "details": [
                {
                  "id": 1,
                  "name": "eSewa",
                  "descOneFieldName": "Wallet ID",
                  "descOneFieldType": "String",
                  "descOneFixedLength": false,
                  "descOneLength": null,
                  "descOneMinLength": 1,
                  "descOneMaxLength": 35,
                  "descTwoFieldName": "Remarks",
                  "descTwoFieldType": "String",
                  "descTwoFixedLength": false,
                  "descTwoLength": null,
                  "descTwoMinLength": 1,
                  "descTwoMaxLength": 35,
                  "icon": "1542352527067.png",
                  "accountHead": "ESEWA",
                  "accountNumber": "ESEWAWALLET",
                  "minAmount": 100,
                  "maxAmount": null,
                  "status": "Active"
                },
                {
                  "id": 2,
                  "name": "Khalti",
                  "descOneFieldName": "Wallet ID",
                  "descOneFieldType": "String",
                  "descOneFixedLength": false,
                  "descOneLength": null,
                  "descOneMinLength": 1,
                  "descOneMaxLength": 35,
                  "descTwoFieldName": "Remarks",
                  "descTwoFieldType": "String",
                  "descTwoFixedLength": false,
                  "descTwoLength": null,
                  "descTwoMinLength": 1,
                  "descTwoMaxLength": 35,
                  "icon": "ime2.png",
                  "accountHead": "KHALTI",
                  "accountNumber": "KHALTIWALLET",
                  "minAmount": 100,
                  "maxAmount": null,
                  "status": "Active"
                }
              ]
            }
        """.trimIndent()
    }

    private val walletValidationJsonResponse = """
        {
            "status": "Success",
            "code": "M0000",
            "message": "Please confirm your details and proceed",
            "details": null,
            "detail": {
                "message": "Please confirm your details and proceed",
                "status": "success",
                "customerName": null,
                "customerProfileImageUrl": null,
                "validationIdentifier": null
            },
            "packages": null
        }
    """.trimIndent()

    private val serviceChargeJsonResponse = """
        {
            "status": "SUCCESS",
            "code": "M0000",
            "message": "Success",
            "details": 10.0,
            "detail": null,
            "packages": null
        }
    """.trimIndent()

    private val walletLoadJsonResponse = """
        {
            "status": "SUCCESS",
            "code": "M0000",
            "message": "Successfully transferred to wallet",
            "details": {
                "descOneFieldName": "Wallet ID",
                "amount": "1111",
                "walletIcon": "1542352527067.png",
                "walletName": "eSewa",
                "descTwoFieldValue": "test remarks",
                "descTwoFieldName": "Remarks",
                "transactionIdentifier": "903941143070366",
                "descOneFieldValue": "9840173991",
                "accountNumber": "00155555"
            },
            "detail": null,
            "packages": null
        }
    """.trimIndent()


}