package com.guillem.sample_app_rickandmorty.commonsui.compose.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.guillem.sample_app_rickandmorty.commonsui.R

@OptIn(ExperimentalTextApi::class)
private val provider: GoogleFont.Provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs,
)

@OptIn(ExperimentalTextApi::class)
private val SourceSansProFont = GoogleFont(name = "Source Sans Pro")

@OptIn(ExperimentalTextApi::class)
private val SourceSansProFamily = FontFamily(
    Font(SourceSansProFont, provider),
    Font(SourceSansProFont, provider, FontWeight.Bold),
    Font(SourceSansProFont, provider, FontWeight.SemiBold),
    Font(SourceSansProFont, provider, FontWeight.Light),
)

internal val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = SourceSansProFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = SourceSansProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = SourceSansProFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
)
