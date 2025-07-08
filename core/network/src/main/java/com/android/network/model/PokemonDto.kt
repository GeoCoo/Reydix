package com.android.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponseDto(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<PokemonEntryDto>
)

@Serializable
data class PokemonEntryDto(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)