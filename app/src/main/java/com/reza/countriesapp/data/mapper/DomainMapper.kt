package com.reza.countriesapp.data.mapper

interface DomainMapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel
}