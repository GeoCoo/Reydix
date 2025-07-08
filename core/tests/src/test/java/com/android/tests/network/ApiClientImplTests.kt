package com.android.tests.network

import com.android.network.impl.ApiClientImpl
import com.android.network.service.ApiService
import com.android.network.service.MockService
import com.android.tests.mockEventList
import com.android.tests.mockPokemonResponse
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import retrofit2.Response

class ApiClientImplTest {
    private lateinit var apiService: ApiService
    private lateinit var mockService: MockService
    private lateinit var apiClient: ApiClientImpl

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        mockService = mock(MockService::class.java)
        apiClient = ApiClientImpl(apiService, mockService)
    }

    @Test
    fun `retrieveEvents should call mockService and return its response`() = runTest {
        // Given
        val response = Response.success(mockEventList)
        `when`(mockService.retrieveEvents()).thenReturn(response)

        // When
        val result = apiClient.retrieveEvents()

        // Then
        verify(mockService, times(1)).retrieveEvents()
        assert(result === response)
    }

    @Test
    fun `retrievePopularPokemon should call apiService and return its response`() = runTest {
        // Given
        val response = Response.success(mockPokemonResponse)
        `when`(apiService.retrievePopularPokemon()).thenReturn(response)

        // When
        val result = apiClient.retrievePopularPokemon()

        // Then
        verify(apiService, times(1)).retrievePopularPokemon()
        assert(result === response)
    }
}