package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface SchemeChargeRepository{
    suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean = false
    ): ApiResult<SchemeCharge, DataError>
}