package me.davidcosta.github.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class IntExtensionsTest {

    @Test
    fun `transform to short notation when bigger then 1000`() {
        assertEquals("1.1K", 1100.toShortNotation())
    }

    @Test
    fun `transform to short notation when bigger then 1000 with round down`() {
        assertEquals("1.1K", 1110.toShortNotation())
    }

    @Test
    fun `transform to short notation when bigger then 1000 with round up`() {
        assertEquals("1.1K", 1150.toShortNotation())
    }

    @Test
    fun `transform to short notation when smaller then 1000`() {
        assertEquals("900", 900.toShortNotation())
    }
}