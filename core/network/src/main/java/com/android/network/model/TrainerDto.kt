package com.android.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TrainerDto(
    @SerializedName("name") val name: String,
    @SerializedName("pokemons") val pokemons: List<String>
)