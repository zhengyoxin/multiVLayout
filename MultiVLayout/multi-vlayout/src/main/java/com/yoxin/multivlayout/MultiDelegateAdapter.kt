package com.yoxin.multivlayout

import android.content.Context
import android.support.annotation.CheckResult
import android.util.Log
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager

/**
 * Created by zhengyongxin on 2018/2/25.
 * 封装VLayout的[DelegateAdapter]
 */
@Suppress("UNCHECKED_CAST")
open class MultiDelegateAdapter(
        private val context: Context,
        virtualLayoutManager: VirtualLayoutManager,
        private val data: MutableList<Any> = MutableList(10, {}),
        private val typePool: TypePool = MultiTypePool()) : DelegateAdapter(virtualLayoutManager) {

    private val adapters: MutableList<MultiAdapter<*, *>> = mutableListOf()

    /**
     * 注册一种数据类和它所对应的适配器，如果你注册一个已经注册过这个数据类，
     * 它将会覆盖掉原有的适配器。该方法并不是线程安全的，请务必在主线程运行
     *
     * Note: 需要在setAdapter方法之前调用
     *
     * @param clazz 数据类
     * @param adapter 适配器
     * @param
     */
    fun <T> register(clazz: Class<out T>, adapter: Class<out MultiAdapter<*, *>>) {
        checkAndRemoveAllTypesIfNeed(clazz)
        typePool.register(clazz, adapter, DefaultLinker<Any>())
    }

    /**
     * 注册一种数据类和它所对应多种适配器，如果你注册一个已经注册过这个数据类，
     * 它将会覆盖掉原有的适配器。该方法并不是线程安全的，请务必在主线程运行
     *
     * Note: 需要在setAdapter方法之前调用
     *
     * @param clazz 数据类
     * @return [OneToManyFlow] 设置类和适配器一对多的中间流程
     */
    @CheckResult
    fun <T> register(clazz: Class<out T>): OneToManyFlow<T> {
        return OneToManyBuilder(this, clazz)
    }

    /**
     * 注册一个类型池。
     *
     * Note: 需要在setAdapter方法之前调用
     *
     * @param typePool 类型分发池
     */
    fun registerAll(typePool: TypePool) {
        val size = typePool.size()
        for (index in 0..size) {
            registerWithoutChecking(
                    typePool.getClass(index),
                    typePool.getItemViewAdapter(index),
                    typePool.getLinker(index)
            )
        }
    }

    /**
     * 设置列表数据，并刷新列表
     *
     * @param data 数据集合
     */
    open fun setData(data: MutableList<Any>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataChange()
    }

    /**
     * 添加列表数据，并刷新列表
     *
     * @param data 数据集合
     */
    open fun addData(data: MutableList<Any>) {
        this.data.addAll(data)
        notifyDataChange()
    }

    /**
     * 数据刷新
     */
    private fun notifyDataChange() {
        clear()
        adapters.forEach {
            it.onViewDetachedFromWindow(null)
        }
        adapters.clear()
        this.data.forEach {
            val itemViewAdapterClass = typePool.getItemViewAdapter(indexInTypesOf(it))
            val itemViewAdapter = itemViewAdapterClass.newInstance()
            itemViewAdapter.setData(it)
            itemViewAdapter.context = context
            adapters.add(itemViewAdapter)
            this.addAdapter(itemViewAdapter)
        }
        notifyDataSetChanged()
    }

    @Throws(AdapterNotFoundException::class)
    internal fun indexInTypesOf(item: Any): Int {
        val index = typePool.firstIndexOf(item::class.java)
        if (index != -1) {
            val linker = typePool.getLinker(index) as Linker<Any>
            return index + linker.index(item)
        }
        throw AdapterNotFoundException(item::class.java)
    }

    fun getCurrentAdapters(): List<MultiAdapter<*, *>> {
        return (0 until itemCount).mapNotNull { findAdapterByIndex(it) as? MultiAdapter<*, *> }
    }

    private fun checkAndRemoveAllTypesIfNeed(clazz: Class<*>) {
        if (typePool.unregister(clazz)) {
            Log.w(TAG, "You have registered the " + clazz.simpleName + " type. " +
                    "It will override the original binder(s).")
        }
    }

    internal fun registerWithLinker(
            clazz: Class<*>,
            adapter: Class<out MultiAdapter<*, *>>,
            linker: Linker<*>) {
        typePool.register(clazz, adapter, linker)
    }

    /** A safe register method base on the TypePool's safety for TypePool.  */
    private fun registerWithoutChecking(clazz: Class<*>, adapter: Class<out MultiAdapter<*, *>>, linker: Linker<*>) {
        checkAndRemoveAllTypesIfNeed(clazz)
        typePool.register(clazz, adapter, linker)
    }

    companion object {
        const val TAG = "MultiDelegateAdapter"
    }
}