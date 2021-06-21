package com.example.myapplication

import android.net.Uri
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Test
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
        println(Gson().toJson(DevBean, DevBean::class.java))
    }

    object DevBean {
        var text = "1"
        val devList = ArrayList<Dev>()

        init {
            devList.add(Dev())
            devList.add(Dev())
            devList.add(Dev())
            devList.add(Dev())
        }
    }

    class Dev {
        var arg = 2
    }
}