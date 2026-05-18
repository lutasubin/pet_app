package com.weappsinc.screenpet.feature.settings.presentation

/** Muc ngon ngu trong app (settings + first-run). */
data class LanguageOption(
    val tag: String,
    val flagAsset: String,
    val label: String,
)

fun appLanguageOptions(): List<LanguageOption> = listOf(
    LanguageOption("en-GB", "flags/UK.png", "English (UK)"),
    LanguageOption("en-US", "flags/us.png", "English (US)"),
    LanguageOption("pt", "flags/pt.png", "Português"),
    LanguageOption("es-ES", "flags/es.png", "Español"),
    LanguageOption("ja-JP", "flags/jp.png", "日本語"),
    LanguageOption("ko-KR", "flags/kr.png", "한국어"),
    LanguageOption("de", "flags/de.png", "Deutsch"),
    LanguageOption("id", "flags/id.png", "Indonesia"),
    LanguageOption("hi", "flags/in.png", "हिन्दी"),
    LanguageOption("ar", "flags/sa.png", "العربية"),
    LanguageOption("vi", "flags/vn.png", "Tiếng Việt"),
    LanguageOption("tr", "flags/tr.png", "Türkçe"),
)
