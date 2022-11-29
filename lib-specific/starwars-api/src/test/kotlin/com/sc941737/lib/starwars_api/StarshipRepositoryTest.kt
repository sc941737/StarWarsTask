package com.sc941737.lib.starwars_api

import com.sc941737.lib.error.ErrorTracker
import com.sc941737.lib.error.ErrorTrackerImpl
import com.sc941737.lib.network.NetworkHelper
import com.sc941737.lib.starwars_api.remote.Person
import com.sc941737.lib.starwars_api.remote.StarWarsApi
import com.sc941737.lib.starwars_api.remote.StarWarsApiImpl
import com.sc941737.lib.starwars_api.remote.Starship
import io.ktor.client.HttpClient
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.junit.Before
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject

class StarshipRepositoryTest {
    private val repo: StarshipRepository by inject(StarshipRepository::class.java, named("1"))
    private val repoWithDuplicates: StarshipRepository by inject(StarshipRepository::class.java, named("2"))

    @OptIn(DelicateCoroutinesApi::class)
    private val testModule = module {
        val dispatcher = Dispatchers.Default
        val client = HttpClient()
        val networkHelper = NetworkHelper(client, dispatcher)
        val api = StarWarsApiImpl(networkHelper)
        val errorTracker = ErrorTrackerImpl(GlobalScope)
        single<StarshipRepository>(named("1")) {
            MockStarshipRepositoryImpl1(networkHelper, api, errorTracker)
        }
        single<StarshipRepository>(named("2")) {
            MockStarshipRepositoryImpl2(networkHelper, api, errorTracker)
        }
    }

    @Before
    fun setup() {
        startKoin {
            loadKoinModules(testModule)
        }
    }

    @Test
    fun `test starship voyagers validation`() {
        testCrewMembers.forEach {
            repo.addCrewMember(it)
        }
        testPassengers.forEach {
            repo.addCrewMember(it)
        }
        val isValid1 = repo.validateStarshipVoyagers()
        assertTrue(isValid1)

        repo.addCrewMember(testCrewMembers.first()) // Duplicate, should not be added
        val isValid2 = repo.validateStarshipVoyagers()
        assertTrue(isValid2)

        val isValid3 = repoWithDuplicates.validateStarshipVoyagers()
        assertFalse(isValid3)
    }
}

private val testCrewMembersWithDuplicates get(): List<Person> {
    val first = testCrewMembers.first()
    return testCrewMembers.map {
        if (it == testCrewMembers.last()) first // Swap last for a duplicate of first
        else it
    }
}

private val testCrewMembers get() = listOf(
    Person(name="Beru Whitesun lars", birthYear="47BBY", gender="female", id="https://swapi.dev/api/people/7/"),
    Person(name="R5-D4", birthYear="unknown", gender="n/a", id="https://swapi.dev/api/people/8/"),
    Person(name="Biggs Darklighter", birthYear="24BBY", gender="male", id="https://swapi.dev/api/people/9/"),
    Person(name="Obi-Wan Kenobi", birthYear="57BBY", gender="male", id="https://swapi.dev/api/people/10/"),
)

private val testPassengers get() = listOf(
    Person(name="Luke Skywalker", birthYear="19BBY", gender="male", id="https://swapi.dev/api/people/1/"),
    Person(name="C-3PO", birthYear="112BBY", gender="n/a", id="https://swapi.dev/api/people/2/"),
    Person(name="R2-D2", birthYear="33BBY", gender="n/a", id="https://swapi.dev/api/people/3/"),
    Person(name="Darth Vader", birthYear="41.9BBY", gender="male", id="https://swapi.dev/api/people/4/"),
    Person(name="Leia Organa", birthYear="19BBY", gender="female", id="https://swapi.dev/api/people/5/"),
    Person(name="Owen Lars", birthYear="52BBY", gender="male", id="https://swapi.dev/api/people/6/"),
)

open class MockStarshipRepositoryImpl1(
    networkHelper: NetworkHelper,
    api: StarWarsApi,
    errorTracker: ErrorTracker,
) : StarshipRepositoryImpl(networkHelper, api, errorTracker) {
    override val currentStarship: StateFlow<Starship?>
        get() = MutableStateFlow(
            Starship(
                id = "",
                name = "Millenium Falcon",
                passengersCap = 6,
                crewCap = 4,
            )
        )
}

class MockStarshipRepositoryImpl2(
    networkHelper: NetworkHelper,
    api: StarWarsApi,
    errorTracker: ErrorTracker,
) : MockStarshipRepositoryImpl1(networkHelper, api, errorTracker) {
    override val selectedPeople: StateFlow<SelectedPeople>
        get() = MutableStateFlow(
            SelectedPeople(
                crew = testCrewMembersWithDuplicates,
                passengers = testPassengers,
            )
        )
}