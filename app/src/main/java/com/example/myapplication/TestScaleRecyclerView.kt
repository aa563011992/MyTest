package com.example.myapplication

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_test_glide.*
import kotlinx.android.synthetic.main.layout_test_scale_recycler_view.*

class TestScaleRecyclerView : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_test_scale_recycler_view)

        rvList.adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): RecyclerView.ViewHolder {
                val imageView = ImageView(this@TestScaleRecyclerView)
                return MyViewHolder(imageView)
            }

            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                if (holder is MyViewHolder) {
                    holder.onBindViewHolder()
                }
            }

            override fun getItemCount(): Int {
                return 20
            }

        }
        rvList.layoutManager = LinearLayoutManager(this)
//        rvList.addOnItemTouchListener(RecyclerViewScaler())
    }

    private inner class MyViewHolder(val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView) {
        fun onBindViewHolder() {
            Glide.with(this@TestScaleRecyclerView)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.people.com.cn%2Fmediafile%2Fpic%2F20140711%2F98%2F13163903824065219298.jpg&refer=http%3A%2F%2Fwww.people.com.cn&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1626492740&t=489fd00ac4dc2b4480c487be31106ee8")
                .into(imageView)
        }
    }
}