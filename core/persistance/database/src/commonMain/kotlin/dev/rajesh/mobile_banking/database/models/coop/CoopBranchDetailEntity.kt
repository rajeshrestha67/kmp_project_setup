package dev.rajesh.mobile_banking.database.models.coop

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoopBranchDetailEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
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
    val latitude: String,
    val longitude: String,
    val nchl: String,
    val fax: String,
    val telephoneNumber: String,
    val branchManager: String,
    val createdDate: String,
    val status: String,
    val info: String
)