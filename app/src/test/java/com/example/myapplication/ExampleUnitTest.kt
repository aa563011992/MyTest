package com.example.myapplication

import android.net.Uri
import org.junit.Test

import org.junit.Assert.*
import java.net.URLDecoder
import java.util.concurrent.atomic.AtomicInteger

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
        println(Uri.decode("1+1 =2") + "~~~" + URLDecoder.decode("1+1 =2"))
    }

    var i = AtomicInteger(0)

    @Test
    fun test1() {
        Thread {
            for (j in 0 until 10000) {
                i.incrementAndGet()
            }
            println("i=${i}")
        }.start()
        Thread {
            for (j in 0 until 10000) {
                i.incrementAndGet()
            }
            println("i=${i}")
        }.start()
    }
}