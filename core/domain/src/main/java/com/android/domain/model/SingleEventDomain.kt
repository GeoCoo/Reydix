package com.android.domain.model

import com.android.network.model.EventDto
import com.android.network.model.TrainerDto

data class SingleEventDomain(
    val name: String,
    val date: String,
    val location: String,
    val imgUrl: String,
    val isHighlighted: Boolean,
    val trainers: List<TrainerDomain>
)

data class TrainerDomain(
    val name: String,
    val pokemons: List<String>
)

fun TrainerDto.toDomain(): TrainerDomain {
    return TrainerDomain(
        name = this.name,
        pokemons = this.pokemons
    )
}

fun List<TrainerDto>.toDomain(): List<TrainerDomain> {
    return this.map { it.toDomain() }
}

fun EventDto.toDomain(): SingleEventDomain {
    return SingleEventDomain(
        name = this.name,
        date = this.date,
        location = this.location,
        imgUrl = this.imageUrl,
        isHighlighted = this.isHighlighted,
        trainers = this.trainers.toDomain()
    )
}