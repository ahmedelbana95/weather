package com.app.task

import com.app.task.ui.home.CityValidator
import org.junit.Assert.assertFalse
import org.junit.Test
import org.junit.Assert.assertTrue


class CityValidatorTest {
    @Test
    fun cityValidator_CorrectCitySimple_ReturnsTrue() {
        assertTrue(CityValidator.isValidCity("Alex"))
    }

    @Test
    fun cityValidator_CorrectCity_ReturnsTrue() {
        assertTrue(CityValidator.isValidCity("CAIRO"))
    }

    @Test
    fun cityValidator_WrongCity_ReturnsFalse() {
        assertFalse(CityValidator.isValidCity("CAIRO@@"))
    }
}