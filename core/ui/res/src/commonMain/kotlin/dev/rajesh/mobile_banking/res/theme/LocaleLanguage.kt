package dev.rajesh.mobile_banking.res.theme

import dev.rajesh.mobile_banking.res.SharedRes
import org.jetbrains.compose.resources.StringResource

enum class LocaleLanguage(val langCode: String, val displayName: StringResource) {
    ENGLISH("en", SharedRes.Strings.english),
    Nepali("ne-rNP", SharedRes.Strings.nepali);

    companion object {
        private val typeMap =
            enumValues<LocaleLanguage>().associateBy { it.langCode }

        fun get(langCode: String): LocaleLanguage = LocaleLanguage.typeMap[langCode] ?: ENGLISH

        val list: List<LocaleLanguage>
            get() = LocaleLanguage.entries.toList().map { it }
    }
}