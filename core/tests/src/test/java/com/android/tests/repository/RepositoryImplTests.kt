package com.android.tests.repository

import com.android.common.model.CommonError
import com.android.data.api.EventsResponse
import com.android.data.api.PokemonsRepository
import com.android.data.api.PokemonsResponse
import com.android.data.impl.PokemonRepositoryImpl
import com.android.network.api.ApiClient
import com.android.tests.CoroutineTestRule
import com.android.tests.mockEventList
import com.android.tests.mockPokemonResponse
import com.android.tests.runFlowTest
import com.android.tests.runTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryImplTests {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Spy
    private lateinit var apiClient: ApiClient

    private lateinit var repository: PokemonsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = PokemonRepositoryImpl(apiClient)
    }

    @After
    fun tearDown() {
        coroutineRule.cancelScopeAndDispatcher()
    }

    @Test
    fun `getEvents emits Success when API returns success`() =
        coroutineRule.runTest {
            // Given
            val mockEventResponse = mockEventList
            Mockito.`when`(apiClient.retrieveEvents())
                .thenReturn(Response.success(mockEventResponse))

            // When / Then
            repository.getEvents().runFlowTest {
                assertEquals(
                    EventsResponse.Success(eventResponse = mockEventResponse),
                    awaitItem()
                )
            }
        }


    @Test
    fun `getEvents emits Error when API returns error response`() =
        coroutineRule.runTest {
            // Given
            val errorBody = """{"message":"error"}""".toResponseBody("application/json".toMediaType())
            Mockito.`when`(apiClient.retrieveEvents())
                .thenReturn(Response.error(500, errorBody))

            // When / Then
            repository.getEvents().runFlowTest {
                assertEquals(
                    EventsResponse.Error(CommonError("error")),
                    awaitItem()
                )
            }
        }

    @Test
    fun `getEvents emits Failed when exception is thrown`() =
        coroutineRule.runTest {
            // Given
            Mockito.`when`(apiClient.retrieveEvents())
                .thenThrow(RuntimeException("Network failure"))

            // When / Then
            repository.getEvents().runFlowTest {
                assertEquals(
                    EventsResponse.Failed(errorMsg = "Network failure"),
                    awaitItem()
                )
            }
        }

    @Test
    fun `getPopularPokemons emits Success when API returns success`() =
        coroutineRule.runTest {
            // Given
            val mockResponse = mockPokemonResponse
            Mockito.`when`(apiClient.retrievePopularPokemon())
                .thenReturn(Response.success(mockResponse))

            // When / Then
            repository.getPopularPokemons().runFlowTest {
                assertEquals(
                    PokemonsResponse.Success(popularPokemonsResponse = mockResponse.results),
                    awaitItem()
                )
            }
        }

    @Test
    fun `getPopularPokemons emits Error when API returns error response`() =
        coroutineRule.runTest {
            // Given
            val errorBody = """{"message":"error"}""".toResponseBody("application/json".toMediaType())
            Mockito.`when`(apiClient.retrievePopularPokemon())
                .thenReturn(Response.error(400, errorBody))

            // When / Then
            repository.getPopularPokemons().runFlowTest {
                assertEquals(
                    PokemonsResponse.Error(CommonError("error")),
                    awaitItem()
                )
            }
        }

    @Test
    fun `getPopularPokemons emits Failed when exception is thrown`() =
        coroutineRule.runTest {
            // Given
            Mockito.`when`(apiClient.retrievePopularPokemon())
                .thenThrow(RuntimeException("Network error"))

            // When / Then
            repository.getPopularPokemons().runFlowTest {
                assertEquals(
                    PokemonsResponse.Failed(errorMsg = "Network error"),
                    awaitItem()
                )
            }
        }
}
