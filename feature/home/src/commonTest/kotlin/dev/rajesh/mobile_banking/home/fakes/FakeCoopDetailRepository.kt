package dev.rajesh.mobile_banking.home.fakes

import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.aboutus.domain.repository.CoopDetailRepository
import dev.rajesh.mobile_banking.model.network.DataError
import dev.rajesh.mobile_banking.networkhelper.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCoopDetailRepository : CoopDetailRepository {

    var coopDetail: CoopDetail = CoopDetail(
        id = 1,
        created = "",
        lastModified = "",
        version = 0,
        contactNumber = "",
        facebookUrl = "",
        registerUrl = "",
        email = "",
        web = "",
        address = "",
        splashScreenImageUrl = "",
        appPrimaryColor = "",
        showInterestRate = false,
        showChatSetting = false,
        showForceAppUpdate = false,
        fundTransferViaMobileNumber = false,
        showAnimationForDigitalDakshina = false,
        showSyncAllAccounts = "",
        isRemitHubEnabled = false,
        isDynamicQrEnabled = false,
        bankId = "",
        bankName = "",
        ibankHost = "",
        ibankingPrimaryColor = "",
        ibankingSecondaryColor = "",
        ibankingTertiaryColor = "",
        new = false
    )

    var shouldReturnError = false

    override suspend fun getCoopDetail(clientId: String): Flow<ApiResult<CoopDetail, DataError>> =
        flow {
            if (shouldReturnError) {
                emit(ApiResult.Error(DataError.NetworkError.DataUnknown))
            } else {
                emit(ApiResult.Success(coopDetail))
            }
        }
}