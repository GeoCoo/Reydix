package com.android.network.service

import com.android.network.model.EventDto
import retrofit2.Response
import retrofit2.http.GET

const val mockBaseUrl = "https://mockapi.io/"

interface MockService {

    @GET("events")
    suspend fun retrieveEvents(): Response<List<EventDto>?>
}