package dev.rajesh.mobile_banking.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import dev.rajesh.mobile_banking.database.models.AccountDetailEntity
import dev.rajesh.mobile_banking.database.models.QrEntity
import dev.rajesh.mobile_banking.database.models.UserDetailsEntity

data class UserWithAccounts(
    @Embedded val user: UserDetailsEntity,
    @Relation(
        parentColumn = "mobileNumber",
        entityColumn = "mobileNumber"
    )
    val accountDetails: List<AccountDetailEntity>,

    @Relation(
        parentColumn = "mobileNumber",
        entityColumn = "mobileNumber"
    )
    val qrList: List<QrEntity>
)