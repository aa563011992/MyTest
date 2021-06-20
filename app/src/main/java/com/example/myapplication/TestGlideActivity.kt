package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.bean.ItemList
import com.example.myapplication.network.HttpResultSubscriber
import com.example.myapplication.network.NetWorkManager
import com.example.myapplication.network.scheduler.SchedulerProvider
import com.example.myapplication.network.util.LogUtil
import kotlinx.android.synthetic.main.layout_test_glide.*

class TestGlideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test_glide)
        glide()
        requestJson()

    }

    private fun requestJson() {
        val httpResultSubscriber = object : HttpResultSubscriber<ItemList>() {
            override fun onSuccess(t: ItemList?) {
                LogUtil.i("onSuccess-ItemList:$t")
            }

            override fun onError(code: Int, msg: String?) {
                LogUtil.i("onError-ItemList:$msg")
            }
        }
        NetWorkManager.getRequest().json
            .compose(SchedulerProvider.getInstance().applySchedulers())
            .subscribe(httpResultSubscriber)
    }

    private fun glide() {
        Glide.with(this).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.people.com.cn%2Fmediafile%2Fpic%2F20140711%2F98%2F13163903824065219298.jpg&refer=http%3A%2F%2Fwww.people.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1626492740&t=489fd00ac4dc2b4480c487be31106ee8")
            .into(imageView)
    }
}