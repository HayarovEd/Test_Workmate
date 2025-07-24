package com.edurda77.test.workmate.ui.list_characters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.edurda77.test.workmate.R
import com.edurda77.test.workmate.domain.model.CharacterDetails
import com.edurda77.test.workmate.domain.model.Gender
import com.edurda77.test.workmate.domain.model.StatusCharacter
import com.edurda77.test.workmate.ui.theme.background
import com.edurda77.test.workmate.ui.uikit.ItemCharacter
import com.edurda77.test.workmate.ui.uikit.UiBaseScaffold
import kotlinx.coroutines.flow.distinctUntilChanged
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharactersScreenRoot(
    viewModel: CharactersScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharactersScreenScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreenScreen(
    modifier: Modifier = Modifier,
    state: CharactersScreenState,
    onAction: (CharactersScreenAction) -> Unit,
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    UiBaseScaffold(
        message = state.message,
        topBarContent = {
            OutlinedTextField(
                modifier = modifier
                    .statusBarsPadding()
                    .fillMaxWidth(),
                value = state.queryName,
                textStyle = TextStyle(
                    color = Color.White
                ),
                onValueChange = {
                    onAction(CharactersScreenAction.OnUpdateName(it))
                },
                label = {
                    Text(
                        modifier = modifier,
                        text = stringResource(R.string.enter_name),
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onAction(CharactersScreenAction.OnRefresh)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        fabContent = {
            FloatingActionButton(
                shape = CircleShape,
                containerColor = background,
                onClick = {
                    showBottomSheet = true
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.outline_filter_alt_24),
                    contentDescription = "",
                    tint = Color.White.copy(alpha = 0.8f)
                )
            }
        }
    ) { paddingValues ->
        val lazyListState = rememberLazyGridState()

        LaunchedEffect(state.characters, state.isEnableInternet) {
            if (state.isEnableInternet) {
                snapshotFlow {
                    lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
                }
                    .distinctUntilChanged()
                    .collect { lastVisibleIndex ->
                        if (lastVisibleIndex == state.characters.lastIndex) {
                            if (state.isEnableInternet) {
                                onAction(CharactersScreenAction.OnNextLoad)
                            }
                        }
                    }
            }
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
                BottomSheetContent(
                    status = state.status,
                    gender = state.gender,
                    species = state.species,
                    type = state.type,
                    onClickCansel = {
                        showBottomSheet = false
                    },
                    onClickConfirm = {
                        onAction(CharactersScreenAction.OnRefresh)
                        showBottomSheet = false
                    },
                    onUpdateType = {
                        onAction(CharactersScreenAction.OnUpdateType(it))
                    },
                    onUpdateGender = {
                        onAction(CharactersScreenAction.OnUpdateGender(it))
                    },
                    onUpdateStatus = {
                        onAction(CharactersScreenAction.OnUpdateStatus(it))
                    },
                    onUpdateSpecies = {
                        onAction(CharactersScreenAction.OnUpdateSpecies(it))
                    },
                    onClickResetFilters = {
                        onAction(CharactersScreenAction.OnResetFilters)
                    }
                )
            }
        }
        PullToRefreshBox(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(15.dp),
            isRefreshing = state.isNextLoading,
            onRefresh = { onAction(CharactersScreenAction.OnRefresh) },
            indicator = {
                if (state.isNextLoading) {
                    CircularProgressIndicator(
                        modifier = modifier
                            .align(alignment = Alignment.TopCenter)
                            .size(40.dp)
                    )
                }
            }
        ) {
            if (state.characters.isEmpty()) {
                Box(
                    modifier = modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = modifier
                            .fillMaxWidth(),
                        text = stringResource(R.string.do_data),
                        color = Color.Black,
                        maxLines = 1,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyVerticalGrid(
                    state = lazyListState,
                    modifier = modifier
                        .fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    items(state.characters) { character ->
                        ItemCharacter(
                            characterDetails = character,
                            onClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    CharactersScreenScreen(
        state = CharactersScreenState(
            isNextLoading = true
        ),
        onAction = {}
    )
}

@Preview(showSystemUi = true)
@Composable
private fun Preview2() {
    CharactersScreenScreen(
        state = CharactersScreenState(

            isNextLoading = false,
            characters = listOf(
                CharacterDetails(
                    id = 1,
                    name = "Summer Smith",
                    species = "Human",
                    status = StatusCharacter.ALIVE,
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                    gender = Gender.MALE,
                    location = "Earth (Replacement Dimension)",
                    origin = "Earth (Replacement Dimension)",
                    type = "",
                    episode = listOf("1", "2", "3", "4", "5")
                ),
                CharacterDetails(
                    id = 1,
                    name = "Summer Smith",
                    species = "Human",
                    status = StatusCharacter.ALIVE,
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                    gender = Gender.MALE,
                    location = "Earth (Replacement Dimension)",
                    origin = "Earth (Replacement Dimension)",
                    type = "",
                    episode = listOf("1", "2", "3", "4", "5")
                ),
                CharacterDetails(
                    id = 1,
                    name = "Summer Smith",
                    species = "Human",
                    status = StatusCharacter.ALIVE,
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                    gender = Gender.MALE,
                    location = "Earth (Replacement Dimension)",
                    origin = "Earth (Replacement Dimension)",
                    type = "",
                    episode = listOf("1", "2", "3", "4", "5")
                ),
                CharacterDetails(
                    id = 1,
                    name = "Summer Smith",
                    species = "Human",
                    status = StatusCharacter.ALIVE,
                    imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg",
                    gender = Gender.MALE,
                    location = "Earth (Replacement Dimension)",
                    origin = "Earth (Replacement Dimension)",
                    type = "",
                    episode = listOf("1", "2", "3", "4", "5")
                ),
            )
        ),
        onAction = {}
    )
}