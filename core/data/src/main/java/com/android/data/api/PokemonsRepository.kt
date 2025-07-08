package com.android.data.api

import com.android.common.model.CommonError
import com.android.network.model.EventDto
import com.android.network.model.PokemonEntryDto
import kotlinx.coroutines.flow.Flow


interface PokemonsRepository {
    fun getEvents(): Flow<EventsResponse>
    fun getPopularPokemons(): Flow<PokemonsResponse>
}

sealed class PokemonsResponse {
    data class Success(val popularPokemonsResponse: List<PokemonEntryDto>?) : PokemonsResponse()
    data class Failed(val errorMsg: String?) : PokemonsResponse()
    data class Error(val errorResponse: CommonError) : PokemonsResponse()
}


sealed class EventsResponse {
    data class Success(val eventResponse: List<EventDto>?) : EventsResponse()
    data class Failed(val errorMsg: String?) : EventsResponse()
    data class Error(val errorResponse: CommonError) : EventsResponse()
}