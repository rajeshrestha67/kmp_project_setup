package dev.rajesh.mobile_banking.home.data.remote

import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface BankingServiceRemoteDataSource {

    suspend fun fetchBankingService(): ApiResult<BankingServiceDTO, DataError>
}