package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toSchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.charge.SchemeChargeRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.SchemeChargeRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class SchemeChargeRepositoryImpl(
    private val schemeChargeRemoteDataSource: SchemeChargeRemoteDataSource
) : SchemeChargeRepository {

    override suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeCharge, DataError> {
        return schemeChargeRemoteDataSource.getSchemeCharge(
            amount = amount,
            destinationBankId = destinationBankId,
            isCoop = isCoop
        ).map { dto ->
            dto.toSchemeCharge()
        }
    }
}