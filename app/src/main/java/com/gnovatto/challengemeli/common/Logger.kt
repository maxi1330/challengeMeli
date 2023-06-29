package com.gnovatto.challengemeli.common

import android.util.Log

object LoggerImpl : Logger{
    private const val TAG = "Logger"

    override fun debug(message: String) {
        Log.d(TAG, message)
    }

    override fun info(message: String) {
        Log.i(TAG, message)
    }

    override fun error(message: String) {
        Log.e(TAG, message)
    }
}

interface Logger {
    fun debug(message: String)
    fun info(message: String)
    fun error(message: String)
}
