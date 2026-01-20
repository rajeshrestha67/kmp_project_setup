package dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.remote.bankTransfer

import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankAccountValidationResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.BankListResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.InterBankTransferResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.data.dto.SchemeChargeResponseDTO
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankAccountValidationRequest
import dev.rajesh.mobile_banking.banktransfer.interBankTransfer.domain.model.request.BankTransferRequest
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.post
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.http.Parameters

class InterBankTransferRemoteDataSourceImpl(
    private val httpClient: HttpClient
) : InterBankTransferRemoteDataSource {

    override suspend fun getBankList(): ApiResult<BankListResponseDTO, DataError> {
        return safeCall<BankListResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.BANK_LIST
            )

        }
    }

    override suspend fun validateAccount(
        bankAccountValidationRequest: BankAccountValidationRequest
    ): ApiResult<BankAccountValidationResponseDTO, DataError> {
        return safeCall<BankAccountValidationResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.INTER_BANK_VALIDATION
            ) {
                parameter(
                    "destinationAccountNumber",
                    bankAccountValidationRequest.destinationAccountNumber
                )
                parameter(
                    "destinationAccountName",
                    bankAccountValidationRequest.destinationAccountName
                )
                parameter("destinationBankId", bankAccountValidationRequest.destinationBankId)
            }
        }
    }

    override suspend fun interBankTransfer(
        bankTransferRequest: BankTransferRequest
    ): ApiResult<InterBankTransferResponseDTO, DataError> {
        return safeCall<InterBankTransferResponseDTO> {
            val params = Parameters.build {
                append("account_number", bankTransferRequest.sendersAccountNumber)
                append("destination_bank_id", bankTransferRequest.destinationBankId)
                append("destination_bank_name", bankTransferRequest.destinationBankName)
                append("destination_account_number", bankTransferRequest.receiversAccountNumber)
                append("destination_name", bankTransferRequest.receiversFullName)
                append("amount", bankTransferRequest.amount)
                append("charge", bankTransferRequest.charge)
                append("remarks", bankTransferRequest.remarks)
                append("mPin", bankTransferRequest.mPin)
                append("coop", false.toString())
                append("skipValidation", true.toString())
            }
            httpClient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.IPS_TRANSFER
            ) {
                setBody(FormDataContent(params))
            }
        }
    }

    override suspend fun getSchemeCharge(
        amount: String,
        destinationBankId: String,
        isCoop: Boolean
    ): ApiResult<SchemeChargeResponseDTO, DataError> {

        val params = Parameters.build {
            append("amount", amount)
            append("destinationBankId", destinationBankId)
            append("isCoop", isCoop.toString())
        }

        return safeCall<SchemeChargeResponseDTO> {
            httpClient.post(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.SCHEME_CHARGE
            ) {
                setBody(
                    FormDataContent(params)
                )
            }
        }
    }
}