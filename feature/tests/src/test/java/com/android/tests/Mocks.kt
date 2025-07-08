package com.android.tests

import com.android.domain.model.PokemonDomain
import com.android.domain.model.SingleEventDomain
import com.android.domain.model.TrainerDomain


val mockTrainers = listOf(
    TrainerDomain(name = "Ash", pokemons = listOf("Pikachu", "Bulbasaur")),
    TrainerDomain(name = "Misty", pokemons = listOf("Psyduck", "Staryu"))
)

val mockSingleEventDomains = listOf(
    SingleEventDomain(
        name = "Kanto League",
        date = "2024-07-01",
        location = "Indigo Plateau",
        imgUrl = "https://example.com/kanto.png",
        isHighlighted = true,
        trainers = mockTrainers
    ),
    SingleEventDomain(
        name = "Johto League",
        date = "2024-08-15",
        location = "Silver Conference",
        imgUrl = "https://example.com/johto.png",
        isHighlighted = false,
        trainers = mockTrainers
    )
)

val mockPokemonDomain = listOf(
    PokemonDomain(
        name = "Pikachu",
        imageUrl = "https://example.com/pikachu.png"
    ),
    PokemonDomain(
        name = "Bulbasaur",
        imageUrl = "https://example.com/bulbasaur.png"
    ),
)

val mockTrainersList = listOf(
    TrainerDomain(name = "Ash", pokemons = listOf("pikachu", "charizard")),
    TrainerDomain(name = "Gary", pokemons = listOf("bulbasaur", "alakazam")))