package com.android.domain.interactor.api

import com.android.domain.model.MainData
import com.android.domain.model.PokemonDomain
import com.android.domain.model.SingleEventDomain
import kotlinx.coroutines.flow.Flow

interface PokemonInteractor {
    fun getEvents(): Flow<EventsPartialState>
    fun getPopularPokemons(): Flow<PokemonsPartialState>
    fun fetchData(): Flow<MainDataPartialState>
}


sealed class MainDataPartialState {
    data class Success(val mainData: MainData?) : MainDataPartialState()
    data class Failed(val errorMessage: String) : MainDataPartialState()
    data class Error(val errorMessage: String) : MainDataPartialState()
}

sealed class EventsPartialState {
    data class Success(val events: List<SingleEventDomain>?) : EventsPartialState()
    data class Failed(val errorMessage: String) : EventsPartialState()
    data class Error(val errorMessage: String) : EventsPartialState()
}

sealed class PokemonsPartialState {
    data class Success(val pokemonList: List<PokemonDomain>?) : PokemonsPartialState()
    data class Failed(val errorMessage: String) : PokemonsPartialState()
    data class Error(val errorMessage: String) : PokemonsPartialState()
}
