package com.android.tests.viewModels

import CoroutineTestRule
import com.android.details_screen.ui.DetailsViewModel
import com.android.details_screen.ui.Event
import com.android.details_screen.ui.State
import com.android.domain.model.TrainerDomain
import com.android.tests.mockTrainersList
import com.google.gson.Gson
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import runFlowTest
import runTest

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val coroutineRule = CoroutineTestRule()

    private lateinit var viewModel: DetailsViewModel

    private var initialState = State(isLoading = true, trainers = "", pokemons = listOf())

    private val gson = Gson()

    private lateinit var serializedInfo: String

    @Before
    fun setup() {
        viewModel = DetailsViewModel()
        serializedInfo = gson.toJson(mockTrainersList)
    }

    @After
    fun tearDown() {
        coroutineRule.cancelScopeAndDispatcher()
    }

    @Test
    fun `When HandleData event Then state updates correctly`() =
        coroutineRule.runTest {
            val expectedPairs = listOf(
                "pikachu" to "charizard",
                "bulbasaur" to "alakazam"
            )

            // When
            viewModel.setEvent(Event.HandleData(serializedInfo))

            // Then
            viewModel.viewStateHistory.runFlowTest {
                TestCase.assertEquals(
                    initialState.copy(
                        isLoading = false, trainers = "Ash - Gary", pokemons = expectedPairs
                    ),
                    awaitItem()
                )
            }
        }

    @Test
    fun `When HandleData with empty list Then trainers empty and pokemons empty`() =
        coroutineRule.runTest {
            // Given
            val emptyListJson = gson.toJson(emptyList<TrainerDomain>())

            // When
            viewModel.setEvent(Event.HandleData(emptyListJson))

            // Then
            viewModel.viewStateHistory.runFlowTest {
                TestCase.assertEquals(
                    initialState.copy(isLoading = false, trainers = "", pokemons = listOf()),
                    awaitItem()
                )
            }
        }

}