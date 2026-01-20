package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferDetailDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface InterBankTransferRepository {

    suspend fun getBankList(): ApiResult<List<BankDetail>, DataError>

    suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationDetail, DataError>


    suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferDetail, DataError>

    suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean = false
    ): ApiResult<SchemeCharge, DataError>
}