package com.android.domain.model

import com.android.network.model.PokemonEntryDto

data class PokemonDomain(
    val name: String,
    val imageUrl: String,
)

fun PokemonEntryDto.toDomain(): PokemonDomain {
    return PokemonDomain(
        name = this.name.replaceFirstChar { it.uppercase() },
        imageUrl = this.url.createPokemonUrl()
    )
}

fun List<PokemonEntryDto>?.toDomain(): List<PokemonDomain> {
    return this?.map { it.toDomain() } ?: emptyList()
}

fun String.createPokemonUrl(): String {
    val index = this.split("/".toRegex()).dropLast(1).last()
    return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$index.png"
}
