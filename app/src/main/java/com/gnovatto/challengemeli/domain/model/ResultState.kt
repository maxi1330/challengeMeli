package com.gnovatto.challengemeli.domain.model
sealed class ResultState <out T : Any, out U : String> {
    data class Success <T: Any>(val data : T) : ResultState<T, Nothing>()
    data class Error(val message: String) : ResultState<Nothing, String>()
}