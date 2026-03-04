package dev.rajesh.mobile_banking.aboutus.domain.usecase

import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.aboutus.domain.repository.CoopDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.flow.Flow

class FetchCoopDetailsUseCase(
    private val repository: CoopDetailRepository
) {
    suspend operator fun invoke(
        clientId: String
    ): Flow<ApiResult<CoopDetail, DataError>> {
        return repository.getCoopDetail(clientId)
    }

}