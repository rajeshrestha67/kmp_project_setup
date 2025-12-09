package dev.rajesh.mobile_banking.home.data.mapper

import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.BankingServiceDetailDTO
import dev.rajesh.mobile_banking.home.domain.model.BankingServiceDetail


fun BankingServiceDetailDTO.toBankingService(): BankingServiceDetail{
    return BankingServiceDetail(
        name = name,
        uniqueIdentifier = uniqueIdentifier,
        type = type,
        status= status,
        imageUrl = imageUrl,
        appOrder= appOrder,
        new = new

    )
}

fun BankingServiceDTO.toBankingServiceList(): List<BankingServiceDetail>{
    return details.map{
        it.toBankingService()
    }
}