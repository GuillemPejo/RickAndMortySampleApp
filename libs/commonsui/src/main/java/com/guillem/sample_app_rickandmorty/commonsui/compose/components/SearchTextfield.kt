package com.guillem.sample_app_rickandmorty.commonsui.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.CombinedPreviews

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    onCloseClicked: () -> Unit,
    placeholderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    CustomTextField(
        value = text,
        onValueChange = { value ->
            onTextChanged(value)
        },
        modifier = modifier,
        placeHolderText = placeholderText,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = {
                    onTextChanged("")
                    onCloseClicked()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@CombinedPreviews
@Composable
private fun SearchTextFieldPreview() {
    RickAndMortyTheme {
        Column(Modifier.padding(20.dp)) {
            SearchTextField(
                text = "",
                onTextChanged = {},
                onCloseClicked = {},
                placeholderText = "Search rick and morty..",
                modifier = Modifier,
            )
        }
    }
}
