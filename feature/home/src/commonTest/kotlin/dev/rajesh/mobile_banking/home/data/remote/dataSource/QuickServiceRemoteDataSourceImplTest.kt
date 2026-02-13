package dev.rajesh.mobile_banking.home.data.remote.dataSource

import dev.rajesh.datastore.token.repository.TokenRepository
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSourceImpl
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.testUtils.fakes.FakeTokenRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
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

class QuickServiceRemoteDataSourceImplTest : KoinTest {

    private lateinit var mockEngine: MockEngine

    private val dataSource: QuickServicesRemoteDataSource by inject()

    @BeforeTest
    fun setUp() {
        mockEngine = MockEngine {
            respond(
                content = quickServiceSuccessResponseJson,
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
                                coerceInputValues = true
                                encodeDefaults = true
                            })
                        }
                    }
                }
                singleOf(::QuickServicesRemoteDataSourceImpl).bind<QuickServicesRemoteDataSource>()
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
    fun fetchQuickServices_should_return_success_when_api_responds_ok() = runTest {
        val result = dataSource.getQuickServices()
        println("Rajesh")
        println(result)

        require(result is ApiResult.Success)
        assertTrue(result.data.details?.isNotEmpty() ?: false)
    }


    companion object {
        private val quickServiceSuccessResponseJson = """
            {
              "status": "SUCCESS",
              "code": "M0000",
              "message": "Success",
              "details": [
                {
                  "id": 1,
                  "name": "TOP UP",
                  "imageUrl": "/mbank/serviceIcon/16091503195566734cb71-faea-4319-b360-90631163fa3a.png",
                  "uniqueIdentifier": "topup",
                  "isNew": false,
                  "appOrder": 0,
                  "services": [
                    {
                      "id": 1,
                      "url": "generalMerchantPayment",
                      "uniqueIdentifier": "ntc_prepaid_topup",
                      "service": "NTC PREPAID",
                      "status": "Active",
                      "labelName": "Mobile Number",
                      "labelSize": "20",
                      "labelSample": "9841000000",
                      "labelPrefix": "984,986,974,975,976",
                      "instructions": "Please enter your NT prepaid Number & Amount",
                      "fixedlabelSize": true,
                      "priceInput": false,
                      "notificationUrl": "generalMerchantPayment",
                      "minValue": 20.00,
                      "maxValue": 5000.00,
                      "icon": "16697204017978ce2f9d1-c5f6-4c9d-987c-e83b1141b781.png",
                      "categoryId": 1,
                      "serviceCategoryName": "TOP UP",
                      "webView": true,
                      "isNew": true,
                      "cashBackView":"12",
                      "appOrder": 0,
                      "isSmsMode": false,
                      "ticketStatus": false
                    }
                  ]
                },
                {
                  "id": 2,
                  "name": "INTERNET",
                  "imageUrl": "/mbank/serviceIcon/1609150342269a854cb83-8b78-4ead-9e4c-86459065c00a.png",
                  "uniqueIdentifier": "internet",
                  "isNew": false,
                  "appOrder": 1,
                  "services": [
                    {
                      "id": 11,
                      "url": "generalMerchantPayment",
                      "uniqueIdentifier": "worldlink_online_topup",
                      "service": "WORLDLINK",
                      "status": "Active",
                      "labelName": "Worldlink username",
                      "labelMaxLength": "40",
                      "labelMinLength": "0",
                      "labelSample": "Worldlink Username",
                      "labelPrefix": "",
                      "instructions": "Enter your Worldlink  username and proceed. Amount or package will be displayed for provided user. Select package (if any), select account and enter mPin to recharge your Worldlink account.",
                      "fixedlabelSize": false,
                      "priceInput": false,
                      "notificationUrl": "generalMerchantPayment",
                      "minValue": 1500.00,
                      "maxValue": 25000.00,
                      "icon": "1669719710463d22ba453-6dbc-4cdc-8b4e-ccf626d72b0f.png",
                      "categoryId": 2,
                      "serviceCategoryName": "INTERNET",
                      "webView": true,
                      "isNew": true,
                      "cashBackView":"12",
                      "appOrder": 6,
                      "isSmsMode": false,
                      "ticketStatus": false,
                      "priceRange":"1200-2000"
                    }
                  ]
                }
              ]
            }
        """.trimIndent()
    }

}