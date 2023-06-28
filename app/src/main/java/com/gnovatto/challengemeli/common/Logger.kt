package com.gnovatto.challengemeli.common

import android.util.Log
object Logger {
    private const val TAG = "Logger"

    fun debug(message: String) {
//        if (BuildConfig.DEBUG) {
            Log.d(TAG, message)
//        }
    }

    fun info(message: String) {
        Log.i(TAG, message)
    }

    fun error(message: String) {
        Log.e(TAG, message)
    }
}
