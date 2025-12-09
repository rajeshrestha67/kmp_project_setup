package dev.rajesh.mobile_banking.home.domain.usecase

import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.repository.QuickServiceRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

class FetchQuickServicesUseCase(
    private val quickServiceRepository: QuickServiceRepository
) {
    suspend operator fun invoke(): ApiResult<List<QuickServiceDetail>, DataError> {
        return quickServiceRepository.fetchQuickServices()
    }
}