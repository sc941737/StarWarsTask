package com.sc941737.lib.starwars_api

import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class StarWarsApiUnitTest {

    @Test
    fun `test plusUnique doesn't add copies`() {
        val list = listOf("1", "2", "3")
        val testList = list
            .plusUnique("2")
            .plusUnique("3")
        assertEquals(list, testList)
    }

    @Test
    fun `test plusUnique adds unique objects correctly`() {
        val list = listOf("1", "2", "3")
        val testList = list.plusUnique("4")
        assertContains(testList, "1")
        assertContains(testList, "2")
        assertContains(testList, "3")
        assertContains(testList, "4")
    }

    @Test
    fun `test plusLimited doesn't exceed limit`() {
        val limit = 4
        val list = listOf("1", "2", "3")
        val testList = list
            .plusLimited("4", limit)
            .plusLimited("5", limit)
            .plusLimited("6", limit)
        assertContains(testList, "4")
        assertEquals(limit, testList.size)
    }
}