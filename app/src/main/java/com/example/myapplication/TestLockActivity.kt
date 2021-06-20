package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentActivity

class TestLockActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test_lock)

    }

    @Volatile
    var i = 0
    fun onClick(view: View) {

        Thread {
            for (j in 0 until 10000) {
                add()
            }
            println("i=${i}")
        }.start()
        Thread {
            for (j in 0 until 10000) {
                add()
            }
            println("i=${i}")
        }.start()
    }

    @Synchronized
    fun add() {
        i++
    }

}

val TAG = "TestLockActivity--"

class Foo(var n: Int) {
    var bool = false
    fun foo() {
        for (i in 0 until n) {
            while (!bool) {

            }
            Log.i(TAG, "foo:${i}")
            bool = false
            Thread.sleep(1)
        }
    }

    fun far() {
        for (i in 0 until n) {
            while (bool) {

            }
            Log.i(TAG, "far:${i}")
            bool = true
            Thread.sleep(1)
        }
    }
}