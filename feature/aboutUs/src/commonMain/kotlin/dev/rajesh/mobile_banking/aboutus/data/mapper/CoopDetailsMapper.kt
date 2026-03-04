package dev.rajesh.mobile_banking.aboutus.data.mapper

import dev.rajesh.mobile_banking.aboutus.data.dto.CoopDetailResponseDTO
import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import dev.rajesh.mobile_banking.database.models.CoopDetailEntity

fun CoopDetailResponseDTO.toCoopDetail(): CoopDetail {
    return CoopDetail(
        id = detail.id ?: 0,
        created = detail.created.orEmpty(),
        lastModified = detail.lastModified.orEmpty(),
        version = detail.version ?: 0,
        contactNumber = detail.contactNumber.orEmpty(),
        facebookUrl = detail.facebookUrl.orEmpty(),
        registerUrl = detail.registerUrl.orEmpty(),
        email = detail.email.orEmpty(),
        web = detail.web.orEmpty(),
        address = detail.address.orEmpty(),
        splashScreenImageUrl = detail.splashScreenImageUrl.orEmpty(),
        appPrimaryColor = detail.appPrimaryColor.orEmpty(),
        showInterestRate = detail.showInterestRate ?: false,
        showChatSetting = detail.showChatSetting ?: false,
        showForceAppUpdate = detail.showForceAppUpdate ?: false,
        fundTransferViaMobileNumber = detail.fundTransferViaMobileNumber ?: false,
        showAnimationForDigitalDakshina = detail.showAnimationForDigitalDakshina ?: false,
        showSyncAllAccounts = detail.showSyncAllAccounts.orEmpty(),
        isRemitHubEnabled = detail.isRemitHubEnabled ?: false,
        isDynamicQrEnabled = detail.isDynamicQrEnabled ?: false,
        bankId = detail.bankId.orEmpty(),
        bankName = detail.bankName.orEmpty(),
        ibankHost = detail.ibankHost.orEmpty(),
        ibankingPrimaryColor = detail.ibankingPrimaryColor.orEmpty(),
        ibankingSecondaryColor = detail.ibankingSecondaryColor.orEmpty(),
        ibankingTertiaryColor = detail.ibankingTertiaryColor.orEmpty(),
        new = detail.new ?: false
    )
}

fun CoopDetail.toEntity(clientId: String): CoopDetailEntity {
    return CoopDetailEntity(
        id = id,
        clientId = clientId,
        created = created,
        lastModified = lastModified,
        version = version,
        contactNumber = contactNumber,
        facebookUrl = facebookUrl,
        registerUrl = registerUrl,
        email = email,
        web = web,
        address = address,
        splashScreenImageUrl = splashScreenImageUrl,
        appPrimaryColor = appPrimaryColor,
        showInterestRate = showInterestRate,
        showChatSetting = showChatSetting,
        showForceAppUpdate = showForceAppUpdate,
        fundTransferViaMobileNumber = fundTransferViaMobileNumber,
        showAnimationForDigitalDakshina = showAnimationForDigitalDakshina,
        showSyncAllAccounts = showSyncAllAccounts,
        isRemitHubEnabled = isRemitHubEnabled,
        isDynamicQrEnabled = isDynamicQrEnabled,
        bankId = bankId,
        bankName = bankName,
        ibankHost = ibankHost,
        ibankingPrimaryColor = ibankingPrimaryColor,
        ibankingSecondaryColor = ibankingSecondaryColor,
        ibankingTertiaryColor = ibankingTertiaryColor,
        new = new
    )
}


fun CoopDetailEntity.toDomain(): CoopDetail {
    return CoopDetail(
        id = id,
        created = created,
        lastModified = lastModified,
        version = version,
        contactNumber = contactNumber,
        facebookUrl = facebookUrl,
        registerUrl = registerUrl,
        email = email,
        web = web,
        address = address,
        splashScreenImageUrl = splashScreenImageUrl,
        appPrimaryColor = appPrimaryColor,
        showInterestRate = showInterestRate,
        showChatSetting = showChatSetting,
        showForceAppUpdate = showForceAppUpdate,
        fundTransferViaMobileNumber = fundTransferViaMobileNumber,
        showAnimationForDigitalDakshina = showAnimationForDigitalDakshina,
        showSyncAllAccounts = showSyncAllAccounts,
        isRemitHubEnabled = isRemitHubEnabled,
        isDynamicQrEnabled = isDynamicQrEnabled,
        bankId = bankId,
        bankName = bankName,
        ibankHost = ibankHost,
        ibankingPrimaryColor = ibankingPrimaryColor,
        ibankingSecondaryColor = ibankingSecondaryColor,
        ibankingTertiaryColor = ibankingTertiaryColor,
        new = new
    )
}