package com.android.data.impl

import com.android.common.model.parseError
import com.android.data.api.EventsResponse
import com.android.data.api.PokemonsRepository
import com.android.data.api.PokemonsResponse
import com.android.network.api.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiClient: ApiClient
) :
    PokemonsRepository {
    override fun getEvents(): Flow<EventsResponse> = flow {
        val response = apiClient.retrieveEvents()
        if (response.isSuccessful) {
            emit(EventsResponse.Success(eventResponse = response.body()))
        } else {
            emit(EventsResponse.Error(errorResponse = parseError(response)))
        }
    }.catch {
        emit(
            EventsResponse.Failed(
                errorMsg = it.localizedMessage
            )
        )
    }

    override fun getPopularPokemons(): Flow<PokemonsResponse> = flow {
        val response = apiClient.retrievePopularPokemon()
        if (response.isSuccessful) {
            emit(PokemonsResponse.Success(popularPokemonsResponse = response.body()?.results))
        } else {
            emit(
                PokemonsResponse.Error(
                    errorResponse = parseError(response)
                )
            )
        }

    }.catch {
        emit(
            PokemonsResponse.Failed(
                errorMsg = it.localizedMessage
            )
        )

    }
}