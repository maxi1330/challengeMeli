package com.gnovatto.challengemeli.domain.model

/**
 * Representa el estado de un response de retrofit.
 *
 * Esta clase se utiliza para encapsular el resultado de una petición
 *
 * @param T El tipo de datos para el resultado exitoso.
 * @param U Texto para el mensaje de error.
 */
sealed class ResultState<out T : Any, out U : String> {
    data class Success<T : Any>(val data: T) : ResultState<T, Nothing>()
    data class Error(val message: String) : ResultState<Nothing, String>()
}