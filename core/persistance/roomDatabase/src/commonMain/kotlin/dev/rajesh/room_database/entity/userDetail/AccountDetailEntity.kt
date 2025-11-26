package dev.rajesh.room_database.entity.userDetail

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserDetailEntity::class,
            parentColumns = ["mobileNumber"],
            childColumns = ["userMobileNumber"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AccountDetailEntity(
    @PrimaryKey val id: String,
    val userMobileNumber: String,
    val accountNumber: String,
    val accountType: String,
    val branchCode: String,
    val branchName: String,
    val mainCode: String,
    val mobileBanking: String,
    val primary: String,
    val sms: String
)
