package com.android.tests.network

import com.android.network.api.ApiClient
import com.android.network.impl.ApiClientImpl
import com.android.network.service.ApiService
import com.android.network.service.MockService
import com.android.tests.CoroutineTestRule
import com.android.tests.runTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class ApiClientPokemonAndEventsTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: ApiService
    private lateinit var mockApiService: MockService
    private lateinit var apiClient: ApiClient

    @Before
    fun setUp() {
        mockWebServer = MockWebServer().apply { start() }
        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
        mockApiService = retrofit.create(MockService::class.java)
        apiClient = ApiClientImpl(apiService, mockApiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        coroutineRule.cancelScopeAndDispatcher()
    }

    @Test
    fun `Given valid Pokemon JSON, When retrievePopularPokemon is called, Then it returns parsed response`() =
        coroutineRule.runTest {
            // Given
            val json = """
                {
                  "count": 2,
                  "next": "https://pokeapi.co/api/v2/pokemon?offset=2&limit=2",
                  "previous": null,
                  "results": [
                    {"name": "bulbasaur", "url": "https://pokeapi.co/api/v2/pokemon/1/"},
                    {"name": "ivysaur", "url": "https://pokeapi.co/api/v2/pokemon/2/"}
                  ]
                }
            """.trimIndent()
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))

            // When
            val response = apiClient.retrievePopularPokemon()
            // Then
            assertTrue(response.isSuccessful)
            val dto = response.body()
            assertNotNull(dto)
            assertEquals(2, dto!!.count)
            assertEquals("bulbasaur", dto.results[0].name)
        }

    @Test
    fun `Given error response for retrievePopularPokemon, When called, Then response is not successful`() =
        coroutineRule.runTest {
            // Given
            mockWebServer.enqueue(MockResponse().setResponseCode(500))

            // When
            val response = apiClient.retrievePopularPokemon()

            // Then
            assertFalse(response.isSuccessful)
            assertEquals(500, response.code())
        }

    @Test
    fun `Given valid events JSON, When retrieveEvents is called, Then it returns parsed list`() =
        coroutineRule.runTest {
            // Given
            val json = """
                [
                  {
                    "id": "1",
                    "name": "Spring Festival",
                    "date": "2024-05-01",
                    "location": "Central Park",
                    "imageUrl": "https://example.com/image1.jpg",
                    "type": "Festival",
                    "isHighlighted": true,
                    "trainers": []
                  }
                ]
            """.trimIndent()
            mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(json))

            // When
            val response = apiClient.retrieveEvents()

            // Then
            assertTrue(response.isSuccessful)
            val list = response.body()
            assertNotNull(list)
            assertEquals(1, list!!.size)
            assertEquals("1", list[0].id)
            assertEquals("Spring Festival", list[0].name)
        }

    @Test
    fun `Given error response for retrieveEvents, When called, Then response is not successful`() =
        coroutineRule.runTest {
            // Given
            mockWebServer.enqueue(MockResponse().setResponseCode(404))

            // When
            val response = apiClient.retrieveEvents()

            // Then
            assertFalse(response.isSuccessful)
            assertEquals(404, response.code())
        }
}