package dev.rajesh.mobile_banking.banktransfer.sameBankTransfer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CoopBranchDetail(
    val id: Int = 0,
    val name: String = "",
    val address: String = "",
    val branchCode: String = "",
    val bank: String = "",
    val city: String = "",
    val checker: Boolean = false,
    val maker: Boolean = false,
    val state: String = "",
    val bankId: Int = 0,
    val bankCode: String = "",
    val cbsBranchCode: String = "",
    val email: String = "",
    val branchId: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val nchl: String = "",
    val fax: String = "",
    val telephoneNumber: String = "",
    val branchManager: String = "",
    val createdDate: String = "",
    val status: String = "",
    val info: String = ""
)