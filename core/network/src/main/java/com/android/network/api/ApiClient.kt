package com.android.network.api

import com.android.network.model.EventDto
import com.android.network.model.PokemonResponseDto
import retrofit2.Response

interface ApiClient {
    suspend fun retrievePopularPokemon(): Response<PokemonResponseDto>
    suspend fun retrieveEvents(): Response<List<EventDto>?>

}