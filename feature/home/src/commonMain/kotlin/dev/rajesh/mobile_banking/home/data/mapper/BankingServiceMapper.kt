package dev.rajesh.mobile_banking.home.data.mapper

import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDetailDTO
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail
import dev.rajesh.mobile_banking.networkhelper.BaseUrl
import dev.rajesh.mobile_banking.networkhelper.Constants


fun BankingServiceDetailDTO.toBankingService(): BankingServiceDetail {
    return BankingServiceDetail(
        name = name.orEmpty(),
        uniqueIdentifier = uniqueIdentifier.orEmpty(),
        type = type.orEmpty(),
        status = status.orEmpty(),
        imageUrl = "${Constants.baseUrl}/$imageUrl",
        appOrder = appOrder ?: 0,
        new = new ?: false

    )
}

//BankingServiceResponseDTO
fun BankingServiceDTO.toBankingServiceList(): List<BankingServiceDetail> {
    return details?.map {
        it.toBankingService()
    } ?: emptyList()
}