package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchResponseDTO
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.Constants
import dev.rajesh.mobile_banking.networkhelper.EndPoint
import dev.rajesh.mobile_banking.networkhelper.get
import dev.rajesh.mobile_banking.networkhelper.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.headers

class CoopBranchRemoteDataSourceImpl (
    private val httpClient: HttpClient
): CoopBranchRemoteDataSource {
    override suspend fun getCoopBranches(): ApiResult<CoopBranchResponseDTO, DataError> {
        return safeCall<CoopBranchResponseDTO> {
            httpClient.get(
                baseUrl = BaseUrl.Url,
                endPoint = EndPoint.COOP_BRANCH
            ){
                headers{
                    append("client", Constants.clientId)
                }
            }
        }
    }
}