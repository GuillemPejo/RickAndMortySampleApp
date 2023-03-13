package com.guillem.sample_app_rickandmorty.commonsui.compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.CombinedPreviews

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    placeHolderText: String = "",
    readOnly: Boolean = false,
    isError: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    bottomContent: TextFieldBottomContent = TextFieldBottomContent.Empty,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    title: String? = null
) {
    val typography = AppTheme.typography
    Column(modifier = modifier) {
        if (title != null) {
            Text(
                text = title,
                color = AppTheme.colors.defaultTextColor,
                style = AppTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 12.dp),
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = value,
                onValueChange = { onValueChange(it) },
                enabled = enabled,
                readOnly = readOnly,
                isError = isError,
                singleLine = singleLine,
                maxLines = maxLines,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = AppTheme.colors.defaultTextColor,
                    backgroundColor = AppTheme.colors.background,
                    placeholderColor = AppTheme.colors.unknownDot,
                    trailingIconColor = AppTheme.colors.unknownDot,
                    leadingIconColor = AppTheme.colors.unknownDot,
                    focusedBorderColor = AppTheme.colors.defaultTextColor,
                    unfocusedBorderColor = AppTheme.colors.unknownDot,
                ),
                placeholder = {
                    Text(
                        text = placeHolderText,
                        style = typography.bodyLarge
                    )
                },
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation
            )
        }

        BottomContent(isError, bottomContent)
    }
}

sealed class TextFieldBottomContent {
    object Empty : TextFieldBottomContent()
    data class Error(val text: String) : TextFieldBottomContent()
    data class Hint(val text: String) : TextFieldBottomContent()
    data class Content(val content: @Composable () -> Unit) : TextFieldBottomContent()
}

@Composable
private fun BottomContent(isError: Boolean, bottomContent: TextFieldBottomContent) {
    when (bottomContent) {
        TextFieldBottomContent.Empty -> {}
        is TextFieldBottomContent.Error -> {
            if (isError) {
                Text(
                    text = bottomContent.text,
                    color = AppTheme.colors.error,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 12.sp,
                )
            }
        }
        is TextFieldBottomContent.Hint -> {
            Text(
                text = bottomContent.text,
                color = AppTheme.colors.inverseSurface,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
        is TextFieldBottomContent.Content -> {
            bottomContent.content()
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AutoCompleteTextField(
    modifier: Modifier = Modifier,
    options: List<String>,
    onItemSelected: (String) -> Unit,
    editTextContent: @Composable (Boolean, (Boolean) -> Unit) -> Unit,
) {
    var (isExpanded, setExpanded) = remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {
        editTextContent(isExpanded, setExpanded)

        if (options.isNotEmpty()) {
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    setExpanded(false)
                }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            onItemSelected(selectionOption)
                            setExpanded(false)
                        }
                    ) {
                        Text(
                            text = selectionOption,
                            style = MaterialTheme.typography.body1,
                        )
                    }
                }
            }
        }
    }
}

@CombinedPreviews
@Composable
private fun MyTextFieldPreview() {
    RickAndMortyTheme() {
        TextFieldPreviewContent()
    }
}

@Composable
@Suppress("LongMethod")
private fun TextFieldPreviewContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            CustomTextField(
                value = "Text",
                onValueChange = {},
                title = "Example title"
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                enabled = false,
                onValueChange = {},
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "",
                onValueChange = {},
                placeHolderText = "PlaceHolder",
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                isError = true,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                    )
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                isError = true,
                bottomContent = TextFieldBottomContent.Error("Error message"),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                bottomContent = TextFieldBottomContent.Hint("Hint message"),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
                bottomContent = TextFieldBottomContent.Content(
                    content = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Timer,
                                contentDescription = null,
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "The timer is on",
                                color = AppTheme.colors.inverseSurface,
                            )
                        }
                    }
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = "Text",
                onValueChange = {},
            )
        }
    }
}
