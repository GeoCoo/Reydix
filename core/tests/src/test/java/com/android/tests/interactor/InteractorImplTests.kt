package com.android.tests.interactor

import com.android.common.model.CommonError
import com.android.data.api.EventsResponse
import com.android.data.api.PokemonsRepository
import com.android.data.api.PokemonsResponse
import com.android.domain.interactor.api.EventsPartialState
import com.android.domain.interactor.api.MainDataPartialState
import com.android.domain.interactor.api.PokemonsPartialState
import com.android.domain.interactor.impl.PokemonInteractorImpl
import com.android.domain.model.MainData
import com.android.domain.model.toDomain
import com.android.resources.provider.api.ResourceProvider
import com.android.reydix.core.resources.R
import com.android.tests.CoroutineTestRule
import com.android.tests.mockEventList
import com.android.tests.mockPokemonList
import com.android.tests.runFlowTest
import com.android.tests.runTest
import com.android.tests.toFlow
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonInteractorImplTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Mock
    private lateinit var repository: PokemonsRepository

    @Mock
    private lateinit var resourceProvider: ResourceProvider

    private lateinit var interactor: PokemonInteractorImpl

    private val eventDto = mockEventList.first { it.isHighlighted == false }
    private val highlightedEventDto = mockEventList.first()
    private val pokemonDto = mockPokemonList.first()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        interactor = PokemonInteractorImpl(repository, resourceProvider)
        Mockito.`when`(resourceProvider.getString(R.string.events_fail)).thenReturn("Event fail: ")
        Mockito.`when`(resourceProvider.getString(R.string.pokemons_fetch_fail))
            .thenReturn("Pokemons fail: ")
    }

    @Test
    fun `getEvents emits Success when repository returns Success`() = coroutineRule.runTest {
        getEventsInterceptor(EventsResponse.Success(listOf(eventDto)))
        interactor.getEvents().runFlowTest {
            assertEquals(EventsPartialState.Success(listOf(eventDto.toDomain())), awaitItem())
        }
    }

    @Test
    fun `getEvents emits Failed when repository returns Failed`() = coroutineRule.runTest {
        getEventsInterceptor(EventsResponse.Failed("fail"))
        interactor.getEvents().runFlowTest {
            assertEquals(EventsPartialState.Failed("fail"), awaitItem())
        }
    }

    @Test
    fun `getEvents emits Error when repository returns Error`() = coroutineRule.runTest {
        getEventsInterceptor(EventsResponse.Error(CommonError("err")))
        interactor.getEvents().runFlowTest {
            assertEquals(EventsPartialState.Error("err"), awaitItem())
        }
    }

    @Test
    fun `getPopularPokemons emits Success when repository returns Success`() =
        coroutineRule.runTest {
            getPokemonsInterceptor(PokemonsResponse.Success(listOf(pokemonDto)))
            interactor.getPopularPokemons().runFlowTest {
                assertEquals(
                    PokemonsPartialState.Success(listOf(pokemonDto.toDomain())), awaitItem()
                )
            }
        }

    @Test
    fun `getPopularPokemons emits Failed when repository returns Failed`() = coroutineRule.runTest {
        getPokemonsInterceptor(PokemonsResponse.Failed(errorMsg = "fail"))
        interactor.getPopularPokemons().runFlowTest {
            assertEquals(PokemonsPartialState.Failed(errorMessage = "fail"), awaitItem())
        }
    }

    @Test
    fun `getPopularPokemons emits Error when repository returns Error`() = coroutineRule.runTest {
        getPokemonsInterceptor(PokemonsResponse.Error(CommonError("err")))
        interactor.getPopularPokemons().runFlowTest {
            assertEquals(PokemonsPartialState.Error("err"), awaitItem())
        }
    }

    @Test
    fun `fetchData emits Success when both events and pokemons succeed`() = coroutineRule.runTest {
        getEventsInterceptor(EventsResponse.Success(listOf(eventDto, highlightedEventDto)))
        getPokemonsInterceptor(PokemonsResponse.Success(listOf(pokemonDto)))
        interactor.fetchData().runFlowTest {
            val expected = MainDataPartialState.Success(
                MainData(
                    singleEventDomain = highlightedEventDto.toDomain(),
                    eventList = listOf(eventDto.toDomain()),
                    pokemonList = listOf(pokemonDto.toDomain())
                )
            )
            assertEquals(expected, awaitItem())
        }
    }

    @Test
    fun `fetchData emits Failed when both events and pokemons fail`() = coroutineRule.runTest {
        getEventsInterceptor(EventsResponse.Failed("fail"))
        getPokemonsInterceptor(PokemonsResponse.Failed("fail"))
        interactor.fetchData().runFlowTest {
            assertTrue(awaitItem() is MainDataPartialState.Failed)
        }
    }


    private fun getEventsInterceptor(response: EventsResponse) =
        Mockito.`when`(repository.getEvents()).thenReturn(response.toFlow())

    private fun getPokemonsInterceptor(response: PokemonsResponse) =
        Mockito.`when`(repository.getPopularPokemons()).thenReturn(response.toFlow())
}

