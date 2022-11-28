package com.sc941737.starwarstask

import com.sc941737.starwarstask.ui.selection.transformId
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class MainUnitTest {
    @Test
    fun `test transform id`() {
        val testId = "https://swapi.dev/api/people/10/"
        val testResult = transformId(testId)
        assertEquals("10", testResult)
    }
}