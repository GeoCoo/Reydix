package com.android.tests.viewModels

import CoroutineTestRule
import com.android.domain.interactor.api.MainDataPartialState
import com.android.domain.interactor.api.PokemonInteractor
import com.android.domain.model.MainData
import com.android.main_screen.ui.Effect
import com.android.main_screen.ui.Event
import com.android.main_screen.ui.MainVIewModel
import com.android.main_screen.ui.State
import com.android.resources.provider.api.ResourceProvider
import com.android.reydix.core.resources.R
import com.android.tests.mockPokemonDomain
import com.android.tests.mockSingleEventDomains
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import runFlowTest
import runTest
import toFlow

class MainViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    @Spy
    private lateinit var pokemonInteractor: PokemonInteractor

    @Spy
    private lateinit var resourceProvider: ResourceProvider

    private var initialState = State(isLoading = false)

    private lateinit var viewModel: MainVIewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = MainVIewModel(pokemonInteractor, resourceProvider)
    }

    @After
    fun tearDown() {
        coroutineRule.cancelScopeAndDispatcher()
    }

    @Test
    fun `Given Success partial state When FetchData Then updates state with data`() =
        coroutineRule.runTest {
            // Given
            val event = mockSingleEventDomains.first()
            val pokemons = mockPokemonDomain
            val events = listOf(event)
            val state = MainDataPartialState.Success(
                mainData = MainData(
                    singleEventDomain = event,
                    eventList = events,
                    pokemonList = pokemons
                )
            )

            getDataInterceptor(state)

            // When
            viewModel.setEvent(Event.FetchData)

            // Then
            viewModel.viewStateHistory.runFlowTest {
                TestCase.assertEquals(
                    initialState.copy(
                        isLoading = false,
                        singleEventDomain = event,
                        events = events,
                        popularPokemons = pokemons
                    ),
                    awaitItem()
                )
            }
        }


    @Test
    fun `Given Failed partial state When FetchData Then sets loading false and emits ShowMessage`() =
        coroutineRule.runTest {
            // Given
            val state = MainDataPartialState.Failed(errorMessage = "Something failed")
            getDataInterceptor(state)

            // When
            viewModel.setEvent(Event.FetchData)

            // Then
            viewModel.viewStateHistory.runFlowTest {
                TestCase.assertEquals(
                    initialState.copy(isLoading = false), awaitItem()
                )
            }
            viewModel.effect.runFlowTest {
                TestCase.assertEquals(
                    Effect.ShowMessage(state.errorMessage), awaitItem()
                )
            }
        }

    @Test
    fun `Given Error partial state When FetchData Then sets loading false and emits ShowMessage`() =
        coroutineRule.runTest {
            // Given
            val state = MainDataPartialState.Error("Unexpected error")
            getDataInterceptor(state)

            // When
            viewModel.setEvent(Event.FetchData)

            // Then
            viewModel.viewStateHistory.runFlowTest {
                TestCase.assertEquals(
                    initialState.copy(isLoading = false), awaitItem()
                )
            }
            viewModel.effect.runFlowTest {
                TestCase.assertEquals(
                    Effect.ShowMessage(state.errorMessage), awaitItem()
                )
            }
        }

    @Test
    fun `When PokemonConnect Then emits ShowMessage with localized string`() =
        coroutineRule.runTest {
            // Given
            val pokemonName = "Bulbasaur"
            Mockito.`when`(resourceProvider.getString(R.string.connected))
                .thenReturn("Connected to ")

            // When
            viewModel.setEvent(Event.PokemonConnect(pokemonName))

            // Then
            viewModel.effect.runFlowTest {
                TestCase.assertEquals(
                    Effect.ShowMessage("Connected to Bulbasaur"),
                    awaitItem()
                )
            }
        }


    private fun getDataInterceptor(state: MainDataPartialState) =
        Mockito.`when`(pokemonInteractor.fetchData())
            .thenReturn(state.toFlow())

}