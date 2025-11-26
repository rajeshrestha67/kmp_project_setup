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
data class QrEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userMobileNumber: String,
    val active: String,
    val code: String,
    val imageUrl: String,
    val label: String,
    val sortOrder: Int
)
