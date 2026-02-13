package dev.rajesh.mobile_banking.home.data.remote.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.home.data.mapper.toBankingService
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.repository.BankingServiceRepositoryImpl
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test


class BankingServiceRepositoryImplTest {

    private val remoteDataSource: BankingServiceRemoteDataSource = mock()

    //under test
    private val repository = BankingServiceRepositoryImpl(remoteDataSource)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetch_banking_service_returns_mapped_list_on_success() = runTest {
        val dto = BankingServiceDTO(
            status = "success",
            message = "Banking service fetch successfully",
            code = "M001",
            details = prepareMockResponse()
        )

        //mock behaviour
        everySuspend { remoteDataSource.fetchBankingService() } returns ApiResult.Success(dto)

        //execute
        val result = repository.fetchBankingServices()

        //verify
        when (result) {
            is ApiResult.Success -> {
                val expectedList = dto.details.map { it.toBankingService() }
                result.data shouldBe expectedList
            }

            is ApiResult.Error -> throw AssertionError("Expected success, got error: ${result.error}")
        }
    }

    fun prepareMockResponse() = listOf(
        BankingServiceDetailDTO(
            name = "Bank Transfer",
            uniqueIdentifier = "bank_transfer",
            type = "dashboard",
            status = "Active",
            imageUrl = "/mbank/serviceIcon/1619083189078264233cf-de5f-4235-8d0a-27d332e381f5.png",
            appOrder = 2,
            new = false
        ),
        BankingServiceDetailDTO(
            name = "Load Wallet",
            uniqueIdentifier = "load_wallet",
            type = "dashboard",
            status = "Active",
            imageUrl = "/mbank/serviceIcon/1619081575273387590c0-73c9-476c-9363-66d73f723d02.png",
            appOrder = 3,
            new = false
        )

    )

}