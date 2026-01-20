package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.repository

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toAccountValidation
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toBankList
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toInterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.mapper.toSchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer.InterBankTransferRemoteDataSource
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankAccountValidationDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.BankDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.InterBankTransferDetail
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.SchemeCharge
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.repository.InterBankTransferRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.map

class InterBankTransferRepositoryImpl(
    private val remoteDataSource: InterBankTransferRemoteDataSource
) : InterBankTransferRepository {

    override suspend fun getBankList(): ApiResult<List<BankDetail>, DataError> {
        return remoteDataSource.getBankList().map { dto ->
            dto.toBankList()
        }
    }

    override suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationDetail, DataError> {
        return remoteDataSource
            .validateAccount(bankAccountValidationRequest)
            .map { dto ->
                dto.toAccountValidation()
            }
    }

    override suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferDetail, DataError> {
        return remoteDataSource.interBankTransfer(
            bankTransferRequest
        ).map {
            it.toInterBankTransferDetail()
        }
    }

    override suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeCharge, DataError> {
        return remoteDataSource.getSchemeCharge(
            amount = amount,
            destinationBankId = destinationBankId,
            isCoop = isCoop
        ).map { dto ->
            dto.toSchemeCharge()
        }
    }

}