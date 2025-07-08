package com.android.network.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("date") val date: String,
    @SerializedName("location") val location: String,
    @SerializedName("imageUrl") val imageUrl: String,
    @SerializedName("type") val type: String,
    @SerializedName("isHighlighted") val isHighlighted: Boolean,
    @SerializedName("trainers") val trainers: List<TrainerDto>,
)

