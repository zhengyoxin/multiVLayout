package com.example.zhengyongxin.multivlayout.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.example.zhengyongxin.multivlayout.R
import com.example.zhengyongxin.multivlayout.bean.TextData
import com.yoxin.multivlayout.MultiAdapter
import kotlinx.android.synthetic.main.text.view.*

/**
 * Created by zhengyongxin on 2018/4/3.
 */
class TextAdapter : MultiAdapter<TextData, RecyclerView.ViewHolder>() {
    override fun onCreateLayoutHelper(): LayoutHelper {
        return SingleLayoutHelper()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text.text = getData().text
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.text, null)
        return TextVH(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun setItemViewType(position: Int): Int {
        return 1
    }

    class TextVH(view: View) : RecyclerView.ViewHolder(view)
}