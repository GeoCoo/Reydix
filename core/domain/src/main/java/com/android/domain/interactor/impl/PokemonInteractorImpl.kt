package com.android.domain.interactor.impl

import com.android.common.helpers.safeAsync
import com.android.data.api.EventsResponse
import com.android.data.api.PokemonsRepository
import com.android.data.api.PokemonsResponse
import com.android.domain.interactor.api.EventsPartialState
import com.android.domain.interactor.api.MainDataPartialState
import com.android.domain.interactor.api.PokemonInteractor
import com.android.domain.interactor.api.PokemonsPartialState
import com.android.domain.model.MainData
import com.android.domain.model.toDomain
import com.android.resources.provider.api.ResourceProvider
import com.android.reydix.core.resources.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class PokemonInteractorImpl @Inject constructor(
    private val repository: PokemonsRepository,
    private val resourceProvider: ResourceProvider
) :
    PokemonInteractor {

    override fun getEvents(): Flow<EventsPartialState> = flow {
        repository.getEvents().collect { response ->
            when (response) {
                is EventsResponse.Success -> {
                    emit(
                        EventsPartialState.Success(
                            events = response.eventResponse?.map { it.toDomain() }
                        ))
                }

                is EventsResponse.Failed -> {
                    emit(
                        EventsPartialState.Failed(
                            errorMessage = response.errorMsg
                                ?: resourceProvider.getString(R.string.events_fail)
                        )
                    )
                }

                is EventsResponse.Error -> {
                    emit(EventsPartialState.Error(errorMessage = response.errorResponse.message))
                }
            }
        }
    }.safeAsync {
        EventsPartialState.Failed(it.localizedMessage ?: resourceProvider.genericErrorMessage())
    }

    override fun getPopularPokemons(): Flow<PokemonsPartialState> = flow {
        repository.getPopularPokemons().collect { response ->
            when (response) {
                is PokemonsResponse.Success -> {
                    emit(PokemonsPartialState.Success(pokemonList = response.popularPokemonsResponse?.toDomain()))
                }

                is PokemonsResponse.Failed -> {
                    emit(
                        PokemonsPartialState.Failed(
                            errorMessage = response.errorMsg
                                ?: resourceProvider.getString(R.string.popular_pokemons_fail)
                        )
                    )
                }

                is PokemonsResponse.Error -> {
                    emit(PokemonsPartialState.Error(errorMessage = response.errorResponse.message))
                }
            }
        }
    }.safeAsync {
        PokemonsPartialState.Failed(
            it.localizedMessage ?: resourceProvider.genericErrorMessage()
        )
    }

    override fun fetchData(): Flow<MainDataPartialState> =
        combine(getEvents(), getPopularPokemons()) { events, pokemons ->
            when {
                events is EventsPartialState.Success && pokemons is PokemonsPartialState.Success -> {
                    val eventList = events.events?.filter { !it.isHighlighted } ?: emptyList()
                    val singleEvent = events.events?.firstOrNull { it.isHighlighted }
                    val pokemonList = pokemons.pokemonList.orEmpty()
                    MainDataPartialState.Success(
                        MainData(
                            singleEventDomain = singleEvent,
                            eventList = eventList,
                            pokemonList = pokemonList
                        )
                    )
                }

                events is EventsPartialState.Failed || events is EventsPartialState.Error -> MainDataPartialState.Failed(
                    events.failureMessage()
                )

                pokemons is PokemonsPartialState.Failed || pokemons is PokemonsPartialState.Error -> MainDataPartialState.Failed(
                    pokemons.failureMessage()
                )

                else -> MainDataPartialState.Failed(resourceProvider.getString(R.string.maain_data_fail))
            }
        }.safeAsync {
            MainDataPartialState.Failed(
                it.localizedMessage ?: resourceProvider.genericErrorMessage()
            )
        }

    private fun EventsPartialState?.failureMessage(): String = when (this) {
        is EventsPartialState.Failed ->
            resourceProvider.getString(R.string.events_fail) + this.errorMessage

        is EventsPartialState.Error ->
            resourceProvider.getString(R.string.event_fail) + this.errorMessage

        else ->
            resourceProvider.genericErrorMessage()
    }

    private fun PokemonsPartialState?.failureMessage(): String = when (this) {
        is PokemonsPartialState.Failed ->
            resourceProvider.getString(R.string.popular_pokemons_fail) + this.errorMessage

        is PokemonsPartialState.Error ->
            resourceProvider.getString(R.string.popular_pokemons_fail) + this.errorMessage

        else ->
            resourceProvider.genericErrorMessage()
    }
}