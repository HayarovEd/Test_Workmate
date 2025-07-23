package com.edurda77.test.workmate.ui.list_characters

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

@Composable
fun CharactersScreenScreen(
    modifier: Modifier = Modifier,
    state: CharactersScreenState,
    onAction: (CharactersScreenAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    CharactersScreenScreen(
        state = CharactersScreenState(),
        onAction = {}
    )
}