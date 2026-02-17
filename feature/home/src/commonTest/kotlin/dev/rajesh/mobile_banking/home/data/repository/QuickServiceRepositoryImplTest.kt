package dev.rajesh.mobile_banking.home.data.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.home.data.repository.QuickServiceRepositoryImpl
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class QuickServiceRepositoryImplTest {

    private val remoteDataSource: QuickServicesRemoteDataSource = mock()

    private val repository = QuickServiceRepositoryImpl(remoteDataSource)


    @Test
    fun fetch_quick_service_returns_mapped_list_onSuccess() = runTest {
        val dto = QuickServicesResponseDTO(
            status = "success",
            message = "Quick service fetch successfully",
            code = "M001",
            details = configQuickServiceData()
        )

        everySuspend { remoteDataSource.getQuickServices() } returns ApiResult.Success(dto)

        val result = repository.fetchQuickServices()
        result.shouldBeInstanceOf<ApiResult.Success<List<QuickServiceDetail>>>()

        val data = result.data
        //data shouldHaveSize 2

        data[0].id shouldBe 1
        data[0].uniqueIdentifier shouldBe "topup"
    }

    @Test
    fun fetch_quick_service_returns_error_when_DataSource_fails() = runTest {
        everySuspend { remoteDataSource.getQuickServices() } returns ApiResult.Error(DataError.NetworkError.DataUnknown)

        val result = repository.fetchQuickServices()
        result shouldBe ApiResult.Error(DataError.NetworkError.DataUnknown)
    }

    @Test
    fun fetch_quick_service_returns_empty_list_when_DataSource_returns_empty_list() = runTest {
        val emptyList = emptyList<QuickServiceDetailDTO>()
        val dto = QuickServicesResponseDTO(
            status = "success",
            message = "Quick service fetch successfully",
            code = "M001",
            details = emptyList
        )
        everySuspend { remoteDataSource.getQuickServices() } returns ApiResult.Success(dto)

        val result = repository.fetchQuickServices()
        result.shouldBeInstanceOf<ApiResult.Success<List<QuickServiceDetail>>>()

        result.data shouldBe emptyList()
    }


    fun configQuickServiceData() = listOf(
        QuickServiceDetailDTO(
            id = 1,
            name = "TOP UP",
            imageUrl = "/mbank/serviceIcon/16091503195566734cb71-faea-4319-b360-90631163fa3a.png",
            uniqueIdentifier = "topup",
            isNew = false,
            appOrder = 0,
            services = emptyList()
        ),
        QuickServiceDetailDTO(
            id = 2,
            name = "INTERNET",
            imageUrl = "/mbank/serviceIcon/1609150342269a854cb83-8b78-4ead-9e4c-86459065c00a.png",
            uniqueIdentifier = "internet",
            isNew = false,
            appOrder = 1,
            services = emptyList()
        )
    )


}