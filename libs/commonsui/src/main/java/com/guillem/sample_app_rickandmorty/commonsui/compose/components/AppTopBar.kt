package com.guillem.sample_app_rickandmorty.commonsui.compose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.ThemePreviews

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: (@Composable RowScope.() -> Unit) = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = AppTheme.typography.titleMedium,
            )
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = AppTheme.colors.surface,
        elevation = 0.dp,
    )
}

@ThemePreviews
@Composable
private fun RickAndMortyTopBarPreview() {
    RickAndMortyTheme {
        AppTopBar(
            title = "",
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            },
            actions = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = null
                    )
                }
            }
        )
    }
}
