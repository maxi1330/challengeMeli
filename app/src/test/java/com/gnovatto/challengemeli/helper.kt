package com.gnovatto.challengemeli

import kotlinx.coroutines.runBlocking
import org.mockito.Mockito
import org.junit.Assert.assertThrows

fun <T> safeAny(): T = Mockito.any()

inline fun <reified T : Any> createMock(): T = Mockito.mock(T::class.java)

inline fun <reified T : Throwable> assertThrows(
    noinline executable: suspend () -> Unit
): T = assertThrows(T::class.java) {
    runBlocking {
        executable()
    }
}
