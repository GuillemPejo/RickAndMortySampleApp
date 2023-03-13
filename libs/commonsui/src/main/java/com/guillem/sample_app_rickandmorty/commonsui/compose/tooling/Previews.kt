package com.guillem.sample_app_rickandmorty.commonsui.compose.tooling

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "light",
    group = "themes",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "dark",
    group = "themes",
    uiMode = UI_MODE_NIGHT_YES,
)
annotation class ThemePreviews

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@ThemePreviews
@FontScalePreviews
annotation class CombinedPreviews
