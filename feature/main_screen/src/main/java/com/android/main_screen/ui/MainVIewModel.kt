package com.android.main_screen.ui

import androidx.lifecycle.viewModelScope
import com.android.domain.interactor.api.MainDataPartialState
import com.android.domain.interactor.api.PokemonInteractor
import com.android.domain.model.PokemonDomain
import com.android.domain.model.SingleEventDomain
import com.android.resources.provider.api.ResourceProvider
import com.android.reydix.core.resources.R
import com.android.ui.mvi.MviViewModel
import com.android.ui.mvi.ViewEvent
import com.android.ui.mvi.ViewSideEffect
import com.android.ui.mvi.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class State(
    val isLoading: Boolean,
    val singleEventDomain: SingleEventDomain? = null,
    val events: List<SingleEventDomain>? = listOf(),
    val popularPokemons: List<PokemonDomain>? = listOf(),
) : ViewState

sealed class Event : ViewEvent {
    data object FetchData : Event()
    data class PokemonConnect(val name: String) : Event()
}

sealed class Effect : ViewSideEffect {
    data class ShowMessage(val message: String) : Effect()
}

@HiltViewModel
class MainVIewModel @Inject constructor(
    private val pokemonInteractor: PokemonInteractor,
    private val resourceProvider: ResourceProvider
) : MviViewModel<Event, State, Effect>() {
    override fun setInitialState(): State = State(
        isLoading = true
    )

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.FetchData -> {
                viewModelScope.launch {
                    pokemonInteractor.fetchData().collect {
                        when (it) {
                            is MainDataPartialState.Failed -> {
                                setState { copy(isLoading = false) }
                                setEffect { Effect.ShowMessage(it.errorMessage) }
                            }

                            is MainDataPartialState.Error -> {
                                setState { copy(isLoading = false) }
                                setEffect { Effect.ShowMessage(it.errorMessage) }
                            }

                            is MainDataPartialState.Success -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        singleEventDomain = it.mainData?.singleEventDomain,
                                        events = it.mainData?.eventList,
                                        popularPokemons = it.mainData?.pokemonList
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is Event.PokemonConnect -> {
                val message = resourceProvider.getString(R.string.connected) + event.name
                setEffect { Effect.ShowMessage(message) }
            }
        }
    }
}