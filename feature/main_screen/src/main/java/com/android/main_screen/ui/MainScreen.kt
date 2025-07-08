package com.android.main_screen.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import com.android.common.model.ReydixTopBarType
import com.android.domain.model.TrainerDomain
import com.android.main_screen.composables.FeatureEventCard
import com.android.main_screen.composables.SingleEventItem
import com.android.main_screen.composables.SinglePokemonItem
import com.android.reydix.core.resources.R
import com.android.ui.components.LifecycleEffect
import com.android.ui.components.LoadingIndicator
import com.android.ui.components.ReydixTopBar


@Composable
fun MainScreen(
    onEventClick: (List<TrainerDomain>?) -> Unit
) {
    val viewModel = hiltViewModel<MainVIewModel>()
    val state = viewModel.viewState
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    LifecycleEffect(
        lifecycleOwner = lifecycleOwner, lifecycleEvent = Lifecycle.Event.ON_CREATE
    ) {
        viewModel.setEvent(Event.FetchData)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        topBar = {
            ReydixTopBar(
                type = ReydixTopBarType.MAIN, title = stringResource(R.string.app_name)
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
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    state.value.singleEventDomain?.let {
                        item {
                            FeatureEventCard(it) {
                                onEventClick(it)
                            }
                        }
                    }

                    if (state.value.events?.isNotEmpty() == true)
                        item {
                            Text(stringResource(R.string.weekly_section_title))
                        }

                    item {
                        LazyRow {
                            state.value.events?.let { events ->
                                items(events.size) { index ->
                                    SingleEventItem(events[index]) {
                                        onEventClick(it)
                                    }
                                }
                            }

                        }
                    }

                    if (state.value.popularPokemons?.isNotEmpty() == true)
                        item {
                            Text(stringResource(R.string.popular_pokemons_title))
                        }

                    item {
                        LazyRow {
                            state.value.popularPokemons?.let { pokemons ->
                                items(pokemons.size) { index ->
                                    SinglePokemonItem(
                                        pokemons[index]
                                    ) {
                                        viewModel.setEvent(Event.PokemonConnect(it))
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.flowWithLifecycle(lifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .collect { effect ->
                when (effect) {
                    is Effect.ShowMessage -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}








