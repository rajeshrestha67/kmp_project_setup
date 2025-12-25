package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoopBranchResponseDTO(
    val status: String,
    val code: String,
    val message: String,
    val details: List<CoopBranchDetailDTO>
)

@Serializable
data class CoopBranchDetailDTO(
    val id: Int,
    val name: String,
    val address: String,
    val branchCode: String,
    val bank: String,
    val city: String,
    val checker: Boolean,
    val maker: Boolean,
    val state: String,
    val bankId: Int,
    val bankCode: String,
    val cbsBranchCode: String,
    val email: String,
    val branchId: String,
    val latitude: String?,
    val longitude: String?,
    val nchl: String?,
    val fax: String?,
    val telephoneNumber: String?,
    val branchManager: String?,
    val createdDate: String,
    val status: String,
    val info: String?
)
