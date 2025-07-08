package com.android.network.impl


import com.android.network.api.ApiClient
import com.android.network.model.EventDto
import com.android.network.model.PokemonResponseDto
import com.android.network.service.ApiService
import com.android.network.service.MockService
import retrofit2.Response
import javax.inject.Inject


class ApiClientImpl @Inject constructor(
    private val apiService: ApiService,
    private val mockService: MockService
) : ApiClient {

    override suspend fun retrieveEvents(): Response<List<EventDto>?> =
        mockService.retrieveEvents()

    override suspend fun retrievePopularPokemon(): Response<PokemonResponseDto> =
        apiService.retrievePopularPokemon()
}