package com.android.details_screen.ui

import androidx.lifecycle.viewModelScope
import com.android.common.helpers.deserializeList
import com.android.domain.model.TrainerDomain
import com.android.ui.mvi.MviViewModel
import com.android.ui.mvi.ViewEvent
import com.android.ui.mvi.ViewSideEffect
import com.android.ui.mvi.ViewState
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class State(
    val isLoading: Boolean,
    val trainers: String,
    val pokemons: List<Pair<String, String>>
) : ViewState

sealed class Event : ViewEvent {
    data class HandleData(val info: String?) : Event()

}

sealed class Effect : ViewSideEffect

@HiltViewModel
class DetailsViewModel @Inject constructor(
) : MviViewModel<Event, State, Effect>() {
    override fun setInitialState(): State = State(
        isLoading = true,
        trainers = "",
        pokemons = listOf()
    )

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.HandleData -> {
                viewModelScope.launch {
                    val info = event.info.deserializeList<List<TrainerDomain>>(Gson())

                    val trainers = info
                        ?.joinToString(separator = " - ") { it.name }
                        .orEmpty()

                    val pokemons = info
                        ?.takeIf { it.isNotEmpty() }
                        ?.map { it.pokemons }
                        ?.let { pokemonsLists ->
                            val minSize = pokemonsLists.minOfOrNull { it.size } ?: 0
                            (0 until minSize - 1).flatMap { idx ->
                                pokemonsLists.map { it[idx] to it[idx + 1] }
                            }
                        } ?: emptyList()

                    setState {
                        copy(
                            isLoading = false,
                            trainers = trainers,
                            pokemons = pokemons
                        )
                    }
                }

            }
        }
    }

}