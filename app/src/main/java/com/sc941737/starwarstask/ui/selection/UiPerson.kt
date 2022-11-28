package com.sc941737.starwarstask.ui.selection

import androidx.compose.runtime.Immutable
import com.sc941737.lib.starwars_api.remote.Person
import com.sc941737.lib.ui.filterNotEmpty

@Immutable
data class UiPerson(
    val id: String,
    val fullId: String,
    val name: String,
    val birthYear: String,
    val gender: String,
) {
    val imageName: String
        get() = "avatar_$id"
}

fun transformId(id: String) =
    id.split("/").filterNotEmpty().last()

fun Person.toUiModel() = UiPerson(
    id = transformId(id),
    fullId = id,
    name = name,
    birthYear = birthYear,
    gender = gender,
)