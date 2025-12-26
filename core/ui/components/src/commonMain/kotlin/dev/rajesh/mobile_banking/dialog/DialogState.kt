package dev.rajesh.mobile_banking.dialog

data class DialogState(
    val title: String,
    val description: String,
    val onConfirm: () -> Unit,
    val isVisible: Boolean = false
)