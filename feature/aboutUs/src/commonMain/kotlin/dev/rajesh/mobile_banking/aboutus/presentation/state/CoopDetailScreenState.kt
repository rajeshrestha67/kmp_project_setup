package dev.rajesh.mobile_banking.aboutus.presentation.state

import dev.rajesh.mobile_banking.aboutus.domain.model.CoopDetail
import org.jetbrains.compose.resources.StringResource

data class CoopDetailScreenState(
    val isLoading: Boolean = false,
    val coopDetail: CoopDetail? = null,
    val error: StringResource? = null
)