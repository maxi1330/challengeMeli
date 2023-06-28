package com.gnovatto.challengemeli.common
sealed class ResultState <out T : Any, out U : String> {
    data class Success <T: Any>(val data : T) : ResultState<T, Nothing>()
    data class Error(val msjError: String) : ResultState<Nothing, String>()
}