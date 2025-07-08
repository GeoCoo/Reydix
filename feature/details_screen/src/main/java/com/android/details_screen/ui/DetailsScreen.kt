package com.android.details_screen.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.android.common.model.ReydixTopBarType
import com.android.ui.components.LifecycleEffect
import com.android.ui.components.LoadingIndicator
import com.android.ui.components.ReydixTopBar


@Composable
fun DetailsScreen(
    info: String,
    backAction: () -> Unit
) {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val state = viewModel.viewState
    val lifecycleOwner = LocalLifecycleOwner.current

    LifecycleEffect(
        lifecycleOwner = lifecycleOwner, lifecycleEvent = Lifecycle.Event.ON_CREATE
    ) {
        viewModel.setEvent(Event.HandleData(info))
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = {
            ReydixTopBar(
                type = ReydixTopBarType.DETAIL, title = state.value.trainers, action = backAction
            )
        }
    ) { paddingValues ->
        if (state.value.isLoading)
            LoadingIndicator()
        else
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                LazyColumn {
                    items(state.value.pokemons.size) { index ->
                        MatchItem(
                            pokemon = state.value.pokemons[index]
                        )
                    }
                }
            }
    }
}

@Preview
@Composable
fun DetailsScreenPreview() {
    MatchItem(
        pokemon = "Pikachu" to "Charmander"
    )
}

@Preview
@Composable
fun DetailsScreenPreview2() {
    MatchItem(
        pokemon = "Charmander" to "Pikachu"
    )
}


@Composable
fun MatchItem(
    pokemon: Pair<String, String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = pokemon.first,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp),
            textAlign = TextAlign.Start
        )

        Text(
            text = "vs",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = pokemon.second,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .weight(1f)
                .padding(end = 16.dp),
            textAlign = TextAlign.End
        )
    }
}








