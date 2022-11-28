package com.sc941737.lib.starwars_api.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Starship(
    @SerialName("name") val name: String,
    @SerialName("passengers") val passengersCap: Int,
    @SerialName("crew") val crewCap: Int,
    @SerialName("url") val id: String,
)