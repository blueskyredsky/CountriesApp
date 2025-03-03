package com.reza.countriesapp.factory

import com.reza.feature.home.domain.model.Continent

object ContinentFactory {

    fun createContinents() = listOf(
        Continent(name = "name1", code = "code1"),
        Continent(name = "name2", code = "code2"),
        Continent(name = "name3", code = "code3"),
        Continent(name = "name4", code = "code4"),
        Continent(name = "name5", code = "code5"),
    )
}