package com.android.tests

import com.android.network.model.EventDto
import com.android.network.model.PokemonEntryDto
import com.android.network.model.PokemonResponseDto
import com.android.network.model.TrainerDto

val mockEventList = listOf(
    EventDto(
        id = "1",
        name = "Spring Festival",
        date = "2024-05-01",
        location = "Central Park",
        imageUrl = "https://example.com/image1.jpg",
        type = "Festival",
        isHighlighted = true,
        trainers = listOf(
            TrainerDto(name = "Ash", pokemons = listOf("Pikachu", "Charizard")),
            TrainerDto(name = "Misty", pokemons = listOf("Bulbasaur", "Squirtle"))
        )
    ),
    EventDto(
        id = "2",
        name = "Summer Cup",
        date = "2024-07-15",
        location = "Beach Arena",
        imageUrl = "https://example.com/image2.jpg",
        type = "Competition",
        isHighlighted = false,
        trainers = listOf(
            TrainerDto(name = "Brock", pokemons = listOf("Caterpie", "Metapod"))
        )
    ),
    EventDto(
        id = "3",
        name = "Autumn Showdown",
        date = "2024-09-10",
        location = "City Stadium",
        imageUrl = "https://example.com/image3.jpg",
        type = "Battle",
        isHighlighted = false,
        trainers = listOf(
            TrainerDto(name = "Gary", pokemons = listOf("Eevee", "Gyarados"))
        )
    )
)


val mockPokemonList = listOf(
    PokemonEntryDto(name = "bulbasaur", url = "https://pokeapi.co/api/v2/pokemon/1/"),
    PokemonEntryDto(name = "ivysaur", url = "https://pokeapi.co/api/v2/pokemon/2/"),
    PokemonEntryDto(name = "venusaur", url = "https://pokeapi.co/api/v2/pokemon/3/")
)

val mockPokemonResponse = PokemonResponseDto(
    count = 3,
    next = "https://pokeapi.co/api/v2/pokemon?offset=3&limit=3",
    previous = null,
    results = mockPokemonList
)
