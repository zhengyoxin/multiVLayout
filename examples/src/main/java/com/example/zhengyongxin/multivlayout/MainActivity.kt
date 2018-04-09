package com.example.zhengyongxin.multivlayout

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.example.zhengyongxin.multivlayout.adapter.ImgAdapter
import com.example.zhengyongxin.multivlayout.adapter.RichAdapter
import com.example.zhengyongxin.multivlayout.adapter.TextAdapter
import com.example.zhengyongxin.multivlayout.bean.ImageData
import com.example.zhengyongxin.multivlayout.bean.RichData
import com.example.zhengyongxin.multivlayout.bean.TextData
import com.yoxin.multivlayout.MultiDelegateAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var multiDelegateAdapter: MultiDelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        main_container.apply {
            val vManager = VirtualLayoutManager(this@MainActivity)
            this.layoutManager = vManager
            multiDelegateAdapter = MultiDelegateAdapter(this@MainActivity, vManager).apply {
                register(TextData::class.java, TextAdapter::class.java)
                register(ImageData::class.java, ImgAdapter::class.java)
                register(RichData::class.java, RichAdapter::class.java)
            }
            val delegateAdapter = multiDelegateAdapter
            this.adapter = delegateAdapter
        }

        val data = mutableListOf<Any>()
        (0..10).forEach {
            data.add(TextData("$it"))
            data.add(ImageData(R.drawable.ic_launcher_foreground))
            data.add(RichData("$it", R.drawable.ic_launcher_background))
        }

        multiDelegateAdapter.setData(data)
    }

}
