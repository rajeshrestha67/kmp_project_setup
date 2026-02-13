package dev.rajesh.mobile_banking.home.data.remote.repository

import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.rajesh.mobile_banking.home.data.mapper.toQuickServiceDetail
import dev.rajesh.mobile_banking.home.data.remote.QuickServicesRemoteDataSource
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.home.data.repository.QuickServiceRepositoryImpl
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import io.kotest.matchers.shouldBe
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
        when (result) {
            is ApiResult.Success -> {
                val expectedList = dto.details.map { it.toQuickServiceDetail() }
                result.data shouldBe expectedList
            }

            is ApiResult.Error -> throw AssertionError("Expected success, got error: ${result.error}")
        }

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