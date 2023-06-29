package com.gnovatto.challengemeli

import com.gnovatto.challengemeli.common.Logger

class TestLogger : Logger {
    private val debugMessages = mutableListOf<String>()
    private val infoMessages = mutableListOf<String>()
    private val errorMessages = mutableListOf<String>()

    override fun debug(message: String) {
        debugMessages.add(message)
    }

    override fun info(message: String) {
        infoMessages.add(message)
    }

    override fun error(message: String) {
        errorMessages.add(message)
    }

    fun getDebugMessages(): List<String> = debugMessages
    fun getInfoMessages(): List<String> = infoMessages
    fun getErrorMessages(): List<String> = errorMessages
}