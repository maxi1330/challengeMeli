package com.gnovatto.challengemeli.common.extensions

import com.gnovatto.challengemeli.common.Constants
import com.gnovatto.challengemeli.domain.model.ResultState
import retrofit2.HttpException
import java.io.IOException

fun Throwable.handleExceptionsNetwork(): ResultState.Error {
    return when (this) {
        is IOException -> ResultState.Error(Constants.ERROR_IO_EXCEPTION)
        is HttpException -> ResultState.Error(Constants.ERROR_HTTP_EXCEPTION)
        else -> ResultState.Error(Constants.ERROR_HTTP_NOT_FOUND)
    }
}