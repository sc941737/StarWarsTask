package com.sc941737.lib.starwars_api.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Person(
    @SerialName("name") val name: String,
    @SerialName("birth_year") val birthYear: String,
    @SerialName("gender") val gender: String,
    @SerialName("url") val id: String,
)

@Serializable
data class PeopleResponse(
    @SerialName("results") val queryResults: List<Person>,
)

fun PeopleResponse.toDataModel() = queryResults