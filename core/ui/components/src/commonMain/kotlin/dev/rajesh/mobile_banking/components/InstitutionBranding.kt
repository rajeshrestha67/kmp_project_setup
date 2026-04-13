package dev.rajesh.mobile_banking.components

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import dev.rajesh.mobile_banking.res.SharedRes

@Immutable
data class InstitutionBranding(
    val displayName: String,
    val primaryArgb: ULong,
    val secondaryArgb: ULong,
    val accentArgb: ULong,
    /** Coil model string; empty uses default compose drawable `bank_banner.png`. */
    val bankBannerUri: String,
    val useCustomBrandPalette: Boolean,
) {
    fun resolveBankBannerUri(): String =
        bankBannerUri.ifBlank { SharedRes.getRes("drawable/bank_banner.png") }

    fun primaryColor(): Color = Color(primaryArgb.toLong())
    fun secondaryColor(): Color = Color(secondaryArgb.toLong())
    fun accentColor(): Color = Color(accentArgb.toLong())

    companion object {
        val FALLBACK = InstitutionBranding(
            displayName = "Mobile Banking",
            primaryArgb = 0xFF1f839aUL,
            secondaryArgb = 0xFF57624AUL,
            accentArgb = 0xFF386663UL,
            bankBannerUri = "",
            useCustomBrandPalette = false,
        )
    }
}

object InstitutionBrandingController {
    private var installed: InstitutionBranding = InstitutionBranding.FALLBACK

    fun install(branding: InstitutionBranding) {
        installed = branding
    }

    val current: InstitutionBranding get() = installed
}
