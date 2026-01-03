package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.charge

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface SchemeChargeRemoteDataSource {
    suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean = false
    ): ApiResult<SchemeChargeResponseDTO, DataError>
}