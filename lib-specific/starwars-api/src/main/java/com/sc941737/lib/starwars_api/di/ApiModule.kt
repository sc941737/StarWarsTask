package com.sc941737.lib.starwars_api.di

import com.sc941737.lib.starwars_api.PeopleRepository
import com.sc941737.lib.starwars_api.PeopleRepositoryImpl
import com.sc941737.lib.starwars_api.StarshipRepository
import com.sc941737.lib.starwars_api.StarshipRepositoryImpl
import com.sc941737.lib.starwars_api.remote.StarWarsApi
import com.sc941737.lib.starwars_api.remote.StarWarsApiImpl
import org.koin.dsl.module

val apiModule = module {
    single<StarWarsApi> {
        StarWarsApiImpl(get())
    }
    single<PeopleRepository> {
        PeopleRepositoryImpl(get(), get(), get())
    }
    single<StarshipRepository> {
        StarshipRepositoryImpl(get(), get(), get())
    }
}