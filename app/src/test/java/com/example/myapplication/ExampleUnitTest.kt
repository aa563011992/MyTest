package com.example.myapplication

import android.net.Uri
import com.alibaba.fastjson.JSON
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
        println(Uri.encode("1+1 =2啊") + "~~~" + URLDecoder.decode("1+1 =2啊"))
    }

    var i = AtomicInteger(0)

    @Test
    fun test1() {
        val str = "{\"devList\":[{\"arg\":2},{\"arg\":2},{\"arg\":2},{\"arg\":2}],\"text\":\"1\"}"
        println(Gson().fromJson(str, DevBean::class.java))
        println(JSON.parseObject(str, DevBean::class.java))
    }

    object DevBean {
        val text = "1"
        val devList = ArrayList<Dev>()


        init {
            devList.add(Dev())
            devList.add(Dev())
            devList.add(Dev())
            devList.add(Dev())
        }

        override fun toString(): String {
            return "DevBean(text='$text', devList=$devList)"
        }
    }

    class Dev {
        var arg = 2
        override fun toString(): String {
            return "Dev(arg=$arg)"
        }


    }

}