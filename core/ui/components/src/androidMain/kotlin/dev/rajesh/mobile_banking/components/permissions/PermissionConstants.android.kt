package dev.rajesh.mobile_banking.components.permissions

import android.os.Build

actual val READ_CONTACT_PERMISSION: String
    get() = android.Manifest.permission.READ_CONTACTS
actual val CAMERA_PERMISSION: String
    get() = android.Manifest.permission.CAMERA

actual val GALLERY_PERMISSION: String
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        android.Manifest.permission.READ_MEDIA_IMAGES
    else
        android.Manifest.permission.READ_EXTERNAL_STORAGE
actual val GALLERY_PERMISSION_LIMITED: String
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
    } else {
        ""
    }