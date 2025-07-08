package com.android.domain.model

data class MainData(
    val singleEventDomain: SingleEventDomain?,
    val eventList: List<SingleEventDomain>?,
    val pokemonList: List<PokemonDomain>?,
)