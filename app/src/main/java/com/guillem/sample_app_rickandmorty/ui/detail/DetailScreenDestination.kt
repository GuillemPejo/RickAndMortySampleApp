package com.guillem.sample_app_rickandmorty.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.guillem.sample_app_rickandmorty.R
import com.guillem.sample_app_rickandmorty.commonsui.compose.components.AppTopBar
import com.guillem.sample_app_rickandmorty.commonsui.compose.components.Status
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.CombinedPreviews
import com.guillem.sample_app_rickandmorty.commonsui.navigation.Destination
import com.guillem.sample_app_rickandmorty.commonsui.navigation.DestinationDeclaration
import com.guillem.sample_app_rickandmorty.commonsui.navigation.getWith
import com.guillem.sample_app_rickandmorty.commonsui.utils.collectWithLifecycle
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import org.koin.core.scope.Scope


private const val IMAGE_RATIO_WIDTH = 100
private const val IMAGE_RATIO_HEIGHT = 100

class DetailScreenDestination(
    val characterEncoded: String
) : Destination<Unit>() {
    @Composable
    override fun Content(navEntry: NavBackStackEntry, diScope: Scope, outerArgs: Unit) {
        DetailScreen(diScope.getWith(characterEncoded))
    }

    companion object : DestinationDeclaration<DetailScreenDestination>(
        DetailScreenDestination::class
    )
}

@Composable
fun DetailScreen(detailViewModel: DetailScreenViewModel) {
    val characterDetail by detailViewModel.characterData.collectWithLifecycle()

    DetailScreenContent(
        characterData = characterDetail,
        onBackButtonClicked = detailViewModel::onBackButtonClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailScreenContent(
    characterData: CharacterUI?,
    onBackButtonClicked: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Scaffold(topBar = {
            AppTopBar(navigationIcon = {
                IconButton(onClick = onBackButtonClicked) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        tint = AppTheme.colors.onSurface,
                        contentDescription = null
                    )
                }
            })
        }) {
            Column(
                Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CharacterDetailImage(characterData?.image.orEmpty())

                Text(
                    text = characterData?.name.orEmpty(),
                    style = AppTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                )

                PropertiesItem(
                    gender = characterData?.gender.orEmpty(),
                    species = characterData?.species.orEmpty(),
                    status = characterData?.status.orEmpty()
                )

                listOf(
                    Pair(stringResource(id = R.string.origin), characterData?.origin.orEmpty()),
                    Pair(stringResource(id = R.string.location), characterData?.location.orEmpty()),
                    Pair(stringResource(id = R.string.number_of_episodes_which_appears),
                        characterData?.numberOfEpisodesWhichAppears.toString()
                    ),
                ).forEach { item ->
                    ItemRow(title = item.first, value = item.second)
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                )
            }
        }
    }
}


@Composable
fun CharacterDetailImage(image: String) {
    Box {
        val imageWidth = LocalConfiguration.current.screenWidthDp.dp - (AppTheme.appPadding)
        val imageHeight = imageWidth * IMAGE_RATIO_WIDTH / IMAGE_RATIO_HEIGHT
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .width(imageWidth)
                .height(imageHeight),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun PropertiesItem(
    status: String,
    gender: String,
    species: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Status(status)
        Text(textAlign = TextAlign.Center, text = "[$species · $gender]")
    }
}

@Composable
private fun ItemRow(
    title: String,
    value: String,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.Gray.copy(alpha = 0.1F)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(2F)
                .padding(8.dp), textAlign = TextAlign.Center, text = title
        )
        Text(
            modifier = Modifier
                .weight(2F)
                .padding(8.dp), textAlign = TextAlign.Center, text = value
        )
    }
}

@CombinedPreviews
@Composable
private fun DetailScreenPreview() {
    RickAndMortyTheme {
        DetailScreenContent(
            characterData = CharacterUI(
                gender = "Male",
                id = 0,
                image = "",
                location = "Barcelona",
                name = "Guillem",
                numberOfEpisodesWhichAppears = 20,
                origin = "Barcelona",
                species = "Human",
                status = "Alive"
            ),
            onBackButtonClicked = {})
    }
}