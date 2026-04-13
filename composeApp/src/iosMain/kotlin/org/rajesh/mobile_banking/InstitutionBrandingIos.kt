package org.rajesh.mobile_banking

import dev.rajesh.mobile_banking.components.InstitutionBranding
import dev.rajesh.mobile_banking.components.InstitutionBrandingController
import platform.Foundation.NSBundle
import platform.Foundation.NSString

private fun asString(value: Any?): String? =
    when (value) {
        null -> null
        is NSString -> value.description
        else -> value.toString().takeIf { it.isNotBlank() }
    }

fun installIosInstitutionBrandingFromMainBundle() {
    val dict = NSBundle.mainBundle.infoDictionary
    if (dict == null) {
        InstitutionBrandingController.install(InstitutionBranding.FALLBACK)
        return
    }

    fun str(key: String): String? = asString(dict.objectForKey(key))

    val name = str("MBK_APP_DISPLAY_NAME") ?: InstitutionBranding.FALLBACK.displayName
    val primaryHex = str("MBK_BRAND_PRIMARY")?.removePrefix("0x")?.removePrefix("0X")
    val secondaryHex = str("MBK_BRAND_SECONDARY")?.removePrefix("0x")?.removePrefix("0X")
    val accentHex = str("MBK_BRAND_ACCENT")?.removePrefix("0x")?.removePrefix("0X")

    val primary = primaryHex?.toULongOrNull(16) ?: InstitutionBranding.FALLBACK.primaryArgb
    val secondary = secondaryHex?.toULongOrNull(16) ?: InstitutionBranding.FALLBACK.secondaryArgb
    val accent = accentHex?.toULongOrNull(16) ?: InstitutionBranding.FALLBACK.accentArgb

    val banner = str("MBK_BANK_BANNER_URI").orEmpty()
    val useCustom = str("MBK_USE_CUSTOM_THEME")?.lowercase() != "false"

    InstitutionBrandingController.install(
        InstitutionBranding(
            displayName = name,
            primaryArgb = primary,
            secondaryArgb = secondary,
            accentArgb = accent,
            bankBannerUri = banner,
            useCustomBrandPalette = useCustom,
        )
    )
}
