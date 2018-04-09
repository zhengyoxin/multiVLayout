package com.yoxin.multivlayout

import android.support.annotation.CheckResult

/**
 * Created by zhengyongxin on 2018/2/26.
 * 一对多分发器构建器
 */
class OneToManyBuilder<T>(
        private val multiDelegateAdapter: MultiDelegateAdapter,
        private val clazz: Class<out T>
) : OneToManyFlow<T>, OneToManyEndpoint<T> {

    override fun withLink(linker: Linker<T>) {
        doRegister(linker)
    }

    private lateinit var adapters: MutableList<Class<out MultiAdapter<*, *>>>

    @CheckResult
    override fun to(vararg adapters: Class<out MultiAdapter<T, *>>): OneToManyEndpoint<T> {
        this.adapters = this.adapters
        return this
    }


    override fun withLink(classLinker: ClassLinker<T>) {
        val classLinkerWrapper = ClassLinkerWrapper.wrap(classLinker, adapters)
        doRegister(classLinkerWrapper as Linker<T>)
    }

    private fun doRegister(linker: Linker<T>) {
        for (adapter in adapters) {
            multiDelegateAdapter.registerWithLinker(clazz, adapter, linker)
        }
    }
}