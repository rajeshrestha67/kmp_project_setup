package dev.rajesh.mobile_banking.database.models.userDetail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class QrEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val mobileNumber: String, //foreign Key
    val active: String,
    val code: String,
    val imageUrl: String,
    val label: String,
    val sortOrder: Int
)