package com.reza.countriesapp.domain.model

/**
 * A representation for any kind of result of operations possibly returning data of type [T].
 * Supports data on success and error information on failure.
 */
sealed class ResultState<out T> {

    /**
     * Contains the object of type [T] if it's a success.
     */
    data class Success<T>(val data: T) : ResultState<T>()

    /**
     * Contains an error if it's a failure.
     */
    data class Failure(val error: String) : ResultState<Nothing>()

    /**
     * True if the result is a success, false otherwise.
     */
    val isSuccess: Boolean
        get() = this is Success
}