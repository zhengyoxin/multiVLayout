package com.yoxin.multivlayout

import android.content.Context
import android.support.v7.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter

/**
 * Created by zhengyongxin on 2018/2/25.
 */
@Suppress("UNCHECKED_CAST")
abstract class MultiAdapter<T, VH : RecyclerView.ViewHolder?> : DelegateAdapter.Adapter<VH>() {
    lateinit var context: Context
    private lateinit var data: Any

    fun setData(data: Any) {
        this.data = data
    }

    fun getData(): T {
        return data as T
    }

    /**
     * 强制所有实现MultiAdapter必须设置ItemViewType提供给[getItemViewType]使用，否则刷新时会导致VLayout的ViewHolder复用错误
     */
    abstract fun setItemViewType(position: Int): Int

    override fun getItemViewType(position: Int): Int {
        return setItemViewType(position)
    }
}