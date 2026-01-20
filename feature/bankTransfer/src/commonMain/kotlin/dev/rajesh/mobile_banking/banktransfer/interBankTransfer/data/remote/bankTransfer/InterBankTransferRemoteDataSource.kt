package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult

interface InterBankTransferRemoteDataSource {
    suspend fun getBankList(): ApiResult<BankListResponseDTO, DataError>


    suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationResponseDTO, DataError>

    suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferResponseDTO, DataError>

    suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean = false
    ): ApiResult<SchemeChargeResponseDTO, DataError>
}