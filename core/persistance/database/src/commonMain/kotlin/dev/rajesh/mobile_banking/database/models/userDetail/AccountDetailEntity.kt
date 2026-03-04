package dev.rajesh.mobile_banking.database.models.userDetail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AccountDetailEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val mobileNumber: String, //foreign Key
    val interestRate: String,
    val accountType: String,
    val branchName: String,
    val accruedInterest: String,
    val accountNumber: String,
    val accountHolderName: String,
    val availableBalance: String,
    val branchCode: String,
    val mainCode: String,
    val minimumBalance: String,
    val clientCode: String,
    val actualBalance: String,
    val mobileBanking: String,
    val sms: String,
    val currency: String,
    val primary: String
)