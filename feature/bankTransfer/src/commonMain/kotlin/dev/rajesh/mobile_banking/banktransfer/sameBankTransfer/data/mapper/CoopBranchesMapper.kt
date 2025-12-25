package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.mapper

import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchDetailDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto.CoopBranchResponseDTO
import dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model.CoopBranchDetail

fun CoopBranchDetailDTO.toCoopBranchDetail(): CoopBranchDetail {
    return CoopBranchDetail(
        id = id,
        name = name,
        address = address,
        branchCode = branchCode,
        bank = bank,
        city = city,
        checker = checker,
        maker = maker,
        state = state,
        bankId = bankId,
        bankCode = bankCode,
        cbsBranchCode = cbsBranchCode,
        email = email,
        branchId = branchId,
        latitude = latitude.orEmpty(),
        longitude = longitude.orEmpty(),
        nchl = nchl.orEmpty(),
        fax = fax.orEmpty(),
        telephoneNumber = telephoneNumber.orEmpty(),
        branchManager = branchManager.orEmpty(),
        createdDate = createdDate,
        status = status,
        info = info
    )
}

fun CoopBranchResponseDTO.toCoopBranchList(): List<CoopBranchDetail> {
    return details.map {
        it.toCoopBranchDetail()
    }
}