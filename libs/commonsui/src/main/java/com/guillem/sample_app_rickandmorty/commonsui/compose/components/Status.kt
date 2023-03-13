package com.guillem.sample_app_rickandmorty.commonsui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.ThemePreviews

private const val ALIVE = "Alive"
private const val DEAD = "Dead"

@Composable
fun Status(
    status: String,
) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .border(border = ButtonDefaults.outlinedButtonBorder, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val color = when (status) {
            ALIVE -> AppTheme.colors.greenDot
            DEAD -> AppTheme.colors.redDot
            else -> AppTheme.colors.unknownDot
        }
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = status, color = AppTheme.colors.defaultTextColor
        )
    }

}

@ThemePreviews
@Composable
private fun RickAndMortyTopBarPreview() {
    RickAndMortyTheme {
        Column {
            Status(status = ALIVE)
            Status(status = DEAD)
            Status(status = "Unknown")
        }

    }
}
