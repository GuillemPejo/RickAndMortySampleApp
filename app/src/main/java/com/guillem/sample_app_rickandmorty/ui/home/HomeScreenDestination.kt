package com.guillem.sample_app_rickandmorty.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.guillem.sample_app_rickandmorty.R
import com.guillem.sample_app_rickandmorty.commonsui.compose.components.SearchTextField
import com.guillem.sample_app_rickandmorty.commonsui.compose.components.Status
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.RickAndMortyTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.theme.providers.AppTheme
import com.guillem.sample_app_rickandmorty.commonsui.compose.tooling.CombinedPreviews
import com.guillem.sample_app_rickandmorty.commonsui.navigation.Destination
import com.guillem.sample_app_rickandmorty.commonsui.navigation.DestinationDeclaration
import com.guillem.sample_app_rickandmorty.commonsui.utils.collectWithLifecycle
import com.guillem.sample_app_rickandmorty.ui.model.CharacterUI
import org.koin.core.scope.Scope
import retrofit2.HttpException
import java.io.IOException

class HomeScreenDestination : Destination<Unit>() {
    @Composable
    override fun Content(navEntry: NavBackStackEntry, diScope: Scope, outerArgs: Unit) {
        HomeScreen(diScope.get())
    }

    companion object : DestinationDeclaration<HomeScreenDestination>(
        HomeScreenDestination::class
    )
}

@Composable
fun HomeScreen(homeViewModel: HomeScreenViewModel) {
    val lazyCharacters = homeViewModel.characters.collectAsLazyPagingItems()
    val searchText by homeViewModel.searchTextFieldText.collectWithLifecycle()

    HomeScreenContent(
        charactersData = lazyCharacters,
        onItemClick = homeViewModel::onItemClick,
        onTextChanged = { homeViewModel.searchTextFieldText.value = it },
        searchText = searchText,
        onSearchButtonClicked = homeViewModel::onSearchButtonClicked,
        )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun HomeScreenContent(
    charactersData: LazyPagingItems<CharacterUI>,
    onItemClick: (CharacterUI) -> Unit,
    onTextChanged: (String) -> Unit,
    searchText: String,
    onSearchButtonClicked: () -> Unit,
    ) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            SearchBarItem(
                searchText = searchText,
                focusManager = focusManager,
                keyboardController = keyboardController,
                onTextChanged = onTextChanged,
                onSearchButtonClicked = onSearchButtonClicked
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.appPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                items(charactersData) { character ->
                    character?.let {
                        CharacterItem(item = it, onItemClick = onItemClick)
                    }
                }

                when (charactersData.loadState.append) {
                    is LoadState.Error -> {
                        item { ShowErrorItem(charactersData.loadState.append as LoadState.Error) }
                    }
                    LoadState.Loading -> {
                        item {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            )
                        }
                    }
                    is LoadState.NotLoading -> {}
                }
            }
        }
    }
}

@Composable
fun ShowErrorItem(e: LoadState.Error) {
    val message = when (val errorThrowable = e.error) {
        is IOException -> errorThrowable.message.orEmpty()
        is HttpException -> "${errorThrowable.message.orEmpty()} with code ${errorThrowable.code()}"
        else -> errorThrowable.message.orEmpty()
    }
    Text(
        text = "Opss, it seems there has been an error: $message",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(border = ButtonDefaults.outlinedButtonBorder, shape = RoundedCornerShape(8.dp)),
    )
}

@ExperimentalComposeUiApi
@Composable
fun SearchBarItem(
    searchText: String,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    onTextChanged: (String) -> Unit,
    onSearchButtonClicked: () -> Unit,
) {
    SearchTextField(
        modifier = Modifier.padding(
            bottom = 8.dp,
            start = AppTheme.appPadding,
            end = AppTheme.appPadding,
        ),
        text = searchText,
        onTextChanged = onTextChanged,
        onCloseClicked = onSearchButtonClicked,
        placeholderText = stringResource(R.string.search_bar_hint),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = {
            onSearchButtonClicked()
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}


@Composable
fun CharacterItem(
    item: CharacterUI,
    onItemClick: (CharacterUI) -> Unit,
) {
    Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) }
            .padding(vertical = 8.dp)
            .border(
                border = ButtonDefaults.outlinedButtonBorder,
                shape = RoundedCornerShape(8.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        CharacterImage(modifier = Modifier.padding(8.dp), imageUrl = item.image)

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CharacterTitle(item.name, item.gender, item.species)

            Status(item.status)

        }
    }
}

@Composable
fun CharacterTitle(name: String, gender: String, species: String) {
    Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = name, textAlign = TextAlign.Center, style = AppTheme.typography.titleMedium)
        Text(
            modifier = Modifier.alpha(0.7F),
            text = "[$species · $gender]",
            textAlign = TextAlign.Center,
            style = AppTheme.typography.titleSmall
        )
    }
}

@Composable
fun CharacterImage(
    imageUrl: String, modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .size(width = 108.dp, height = 148.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@CombinedPreviews
@Composable
private fun HomeScreenPreview() {
    RickAndMortyTheme {
        CharacterItem(item = CharacterUI(
            gender = "Male",
            id = 0,
            image = "https://i.pinimg.com/736x/92/30/24/92302412968f3769ba6f1450ea7f52ff.jpg",
            location = "null",
            name = "Guillem",
            origin = "Earth",
            species = "Human",
            status = "Dead",
            numberOfEpisodesWhichAppears = 2,
        ), onItemClick = {})
    }
}