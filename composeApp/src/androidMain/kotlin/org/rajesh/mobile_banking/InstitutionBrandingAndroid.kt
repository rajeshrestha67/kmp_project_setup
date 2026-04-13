package org.rajesh.mobile_banking

import dev.rajesh.mobile_banking.components.InstitutionBranding
import dev.rajesh.mobile_banking.components.InstitutionBrandingController

fun installAndroidInstitutionBrandingFromBuildConfig() {
    InstitutionBrandingController.install(
        InstitutionBranding(
            displayName = BuildConfig.APP_DISPLAY_NAME,
            primaryArgb = BuildConfig.BRAND_PRIMARY_ARGB.toULong(),
            secondaryArgb = BuildConfig.BRAND_SECONDARY_ARGB.toULong(),
            accentArgb = BuildConfig.BRAND_ACCENT_ARGB.toULong(),
            bankBannerUri = BuildConfig.BANK_BANNER_URI,
            useCustomBrandPalette = BuildConfig.USE_CUSTOM_BRAND_THEME,
        )
    )
}
