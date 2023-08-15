package com.reza.countriesapp.domain

data class Country(
    val name: String?,
    val emoji: String?,
    val currency: String?,
    val capital: String?,
    val phone: String?,
    val states: List<String?>?,
    val languages: List<String?>?
)
