package com.android.network.service

import com.android.network.model.PokemonResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val baseUrl = "https://pokeapi.co/api/v2/"

interface ApiService {
    @GET("pokemon")
    suspend fun retrievePopularPokemon(
        @Query("limit") limit: Int=5,
    ): Response<PokemonResponseDto>
}