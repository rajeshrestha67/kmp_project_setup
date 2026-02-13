package dev.rajesh.mobile_banking.home.data.mapper

import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServiceDetailDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.QuickServicesResponseDTO
import dev.rajesh.mobile_banking.home.data.remote.dto.ServiceDTO
import dev.rajesh.mobile_banking.home.domain.model.QuickServiceDetail
import dev.rajesh.mobile_banking.home.domain.model.Service
import dev.rajesh.mobile_banking.networkhelper.Constants

fun QuickServiceDetailDTO.toQuickServiceDetail(): QuickServiceDetail {
    return QuickServiceDetail(
        id = id ?: 0,
        name = name.orEmpty(),
        imageUrl = "${Constants.baseUrl}/${imageUrl}",
        uniqueIdentifier = uniqueIdentifier.orEmpty(),
        isNew = isNew ?: false,
        appOrder = appOrder ?: 0,
        services = services?.map {
            it.toService()
        } ?: emptyList()
    )
}

fun ServiceDTO.toService(): Service {
    return Service(
        id = id ?: 0,
        url = url.orEmpty(),
        uniqueIdentifier = uniqueIdentifier.orEmpty(),
        service = service.orEmpty(),
        status = status.orEmpty(),
        labelName = labelName.orEmpty(),
        labelSize = labelSize.orEmpty(),
        labelSample = labelSample.orEmpty(),
        labelPrefix = labelPrefix.orEmpty(),
        instructions = instructions.orEmpty(),
        fixedlabelSize = fixedlabelSize ?: false,
        priceInput = priceInput ?: false,
        notificationUrl = notificationUrl.orEmpty(),
        minValue = minValue ?: 0.0,
        maxValue = maxValue ?: 0.0,
        icon = icon.orEmpty(),
        categoryId = categoryId ?: 0,
        serviceCategoryName = serviceCategoryName.orEmpty(),
        webView = webView ?: false,
        isNew = isNew ?: false,
        cashBackView = cashBackView.orEmpty(),
        appOrder = appOrder ?: 0,
        isSmsMode = isSmsMode ?: false,
        ticketStatus = ticketStatus ?: false,
        labelMaxLength = labelMaxLength.orEmpty(),
        labelMinLength = labelMinLength.orEmpty(),
        priceRange = priceRange.orEmpty()
    )
}