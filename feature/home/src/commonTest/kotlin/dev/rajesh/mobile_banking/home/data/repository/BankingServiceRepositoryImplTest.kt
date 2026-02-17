package dev.rajesh.mobile_banking.home.data.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.home.data.remote.BankingServiceRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.repository.BankingServiceRepositoryImpl
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test


class BankingServiceRepositoryImplTest {

    private val remoteDataSource: BankingServiceRemoteDataSource = mock()

    //under test
    private val repository = BankingServiceRepositoryImpl(remoteDataSource)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchBankServices_returns_mapped_domain_models_when_datasource_succeeds() = runTest {
        //Arrange
        val dto = BankingServiceDTO(
            status = "success",
            message = "Banking service fetch successfully",
            code = "M001",
            details = prepareMockResponse()
        )

        //mock behaviour
        everySuspend { remoteDataSource.fetchBankingService() } returns ApiResult.Success(dto)

        //Act
        val result = repository.fetchBankingServices()

        //Assert
        result.shouldBeInstanceOf<ApiResult.Success<List<BankingServiceDetail>>>()

        val data = result.data
        data shouldHaveSize 2

        data[0].name shouldBe "Bank Transfer"
        data[0].uniqueIdentifier shouldBe "bank_transfer"

//        verifySuspend(exactly = 1) {
//            remoteDataSource.fetchBankingService()
//        }

    }

    @Test
    fun fetchBankingServices_should_return_error_when_DataSource_fails() = runTest {
        everySuspend { remoteDataSource.fetchBankingService() } returns ApiResult.Error(DataError.NetworkError.DataUnknown)

        val result = repository.fetchBankingServices()
        result shouldBe ApiResult.Error(DataError.NetworkError.DataUnknown)
    }

    @Test
    fun fetchBankingServices_returns_empty_list_when_DataSource_returns_empty_list() = runTest {
        val emptyList = emptyList<BankingServiceDetailDTO>()

        val dto = BankingServiceDTO(
            status = "success",
            message = "Banking service fetch successfully",
            code = "M001",
            details = emptyList
        )
        everySuspend { remoteDataSource.fetchBankingService() } returns ApiResult.Success(dto)

        //Act
        val result = repository.fetchBankingServices()

        //Assert
        result.shouldBeInstanceOf<ApiResult.Success<List<BankingServiceDetail>>>()
        result.data shouldBe emptyList()
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