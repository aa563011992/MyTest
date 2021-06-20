package com.example.myapplication.test4xin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import java.io.*

class TestRebootActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test_reboot)
    }

    fun rebootSixin1(view: View?) {
        try {
            SixinUtil.suExecCommand("reboot")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    fun rebootSixin2(view: View?) {
        try {
            val process = Runtime.getRuntime().exec("su_root")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("reboot\n")
            os.writeBytes("exit\n")
            os.flush()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
    fun rebootSixin3(view: View?) {
        try {
            suExecCommand("reboot")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
    private fun suExecCommand(command: String) {
        val process = Runtime.getRuntime().exec("su_root")
        val os = DataOutputStream(process.outputStream)
        os.writeBytes(command + "\n")
        os.writeBytes("exit\n")
        os.flush()
        val inputStream = process.inputStream
        val inputStreamReader = InputStreamReader(inputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var data = ""
        var s = ""
        while (bufferedReader.readLine().also { s = it } != null) {
            data += s + "\n"
        }
        inputStream?.close()
        inputStreamReader.close()
    }

    fun rebootRuixun(view: View?) {
        try {
            do_shell("reboot")
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun do_shell(str: String): Int {
        var Ret = 0
        val cmd = "$str\nexit\n"
        try {
            val su: Process
            su = Runtime.getRuntime().exec("/system/xbin/su")
            val OS: OutputStream = su.outputStream
            OS.write(cmd.toByteArray())
            try {
                if (su.waitFor() != 0) {
                    Ret = -1
                    throw SecurityException()
                }
            } catch (e: InterruptedException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
                Ret = -1
            }
            su.destroy()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Ret = -1
        }
        return Ret
    }
}