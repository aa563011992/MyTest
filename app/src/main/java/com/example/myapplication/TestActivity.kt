package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.util.PermissionUtil
import kotlinx.android.synthetic.main.layout_test.*
import java.io.File

/**
 * @author: wyl
 * @description:
 * @date: 2021/7/13 14:04
 */
class TestActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test)
        editText.setText(ps)
    }

    val ps = "<dfdf<fdf><><<dd>"
    fun format(view: View) {
        try {
            textView.text = format(editText.text.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun format(text: String): String {
        val result = StringBuffer()
        val rest = StringBuffer(text)
        while (rest.isNotEmpty()) {
            var i = rest.indexOf("<")
            if (i >= 0) {
                val j = rest.indexOf(">")
                if (j > i) {
                    val tmp = rest.substring(i + 1, j)
                    val a = tmp.lastIndexOf("<")
                    if (a >= 0) {
                        result.append(rest.substring(0, a + i + 1))
                    } else {
                        result.append(rest.substring(0, i))
                    }
                    rest.delete(0, j + 1)
                } else if (j in 0 until i) {
                    result.append(rest.substring(0, j + 1))
                    rest.delete(0, j + 1)
                } else {
                    result.append(rest)
                    rest.delete(0, rest.length)
                }

            } else {
                result.append(rest)
                rest.delete(0, rest.length)
            }
        }
        return result.toString()
    }

    fun openAlbum(view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.type = "image/*"
        startActivity(intent)
    }

    val provider = "com.example.myapplication.Doc360FileProvider"
    fun openPhoto(view: View) {
        PermissionUtil.requestStorage({

            //下方是将ImageList集合中的图片路径转换为可供File识别的String数据，
            val value =
                Environment.getExternalStorageDirectory().absolutePath + "/DCIM/Camera/IMG_20191108_110426.jpg"
            val file = File(value)
            Log.i("openPhoto", "file.exists" + file.exists())

            //下方是是通过Intent调用系统的图片查看器的关键代码
            val intent = Intent()
            intent.action = Intent.ACTION_VIEW
            val uri: Uri
            if (Build.VERSION.SDK_INT >= 24) {
                uri = FileProvider.getUriForFile(this, provider, file)
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                uri = Uri.fromFile(file)
            }
            intent.setDataAndType(uri, "image/*")
            startActivity(intent)

        }, this)

    }
}