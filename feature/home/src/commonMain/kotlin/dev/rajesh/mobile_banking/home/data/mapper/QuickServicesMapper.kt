package dev.rajesh.mobile_banking.home.data.mapper

import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.ServiceDTO
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.Service
import dev.rajesh.mobile_banking.networkhelper.Constants


//fun QuickServicesResponseDTO.toQuickServiceList(): QuickServices {
//    return QuickServices(
//        details = details.map { it.toQuickServiceDetail() }
//    )
//}

fun QuickServiceDetailDTO.toQuickServiceDetail(): QuickServiceDetail {
    return QuickServiceDetail(
        id = id,
        name = name,
        imageUrl = "${Constants.baseUrl}/${imageUrl}",
        uniqueIdentifier = uniqueIdentifier,
        isNew = isNew,
        appOrder = appOrder,
        services = services.map {
            it.toService()
        }
    )
}

fun ServiceDTO.toService(): Service {
    return Service(
        id = id,
        url = url,
        uniqueIdentifier = uniqueIdentifier,
        service = service,
        status = status,
        labelName = labelName,
        labelSize = labelSize,
        labelSample = labelSample,
        labelPrefix = labelPrefix,
        instructions = instructions,
        fixedlabelSize = fixedlabelSize,
        priceInput = priceInput,
        notificationUrl = notificationUrl,
        minValue = minValue,
        maxValue = maxValue,
        icon = icon,
        categoryId = categoryId,
        serviceCategoryName = serviceCategoryName,
        webView = webView,
        isNew = isNew,
        appOrder = appOrder,
        isSmsMode = isSmsMode,
        ticketStatus = ticketStatus,
        labelMaxLength = labelMaxLength,
        labelMinLength = labelMinLength,
        priceRange = priceRange
    )
}