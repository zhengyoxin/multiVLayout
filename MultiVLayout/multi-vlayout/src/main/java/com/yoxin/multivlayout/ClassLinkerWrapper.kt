package com.yoxin.multivlayout

@Suppress("UNREACHABLE_CODE")
/**
 * Created by zhengyongxin on 2018/2/26.
 * 应用适配器模式兼容类分发器
 */
class ClassLinkerWrapper<T>(
        private var classLinker: ClassLinker<T>,
        private var adapters: List<Class<out MultiAdapter<*, *>>>)
    : Linker<T> {

    override fun index(t: T): Int {
        val clazz = classLinker.index(t)
        return adapters.indexOfFirst { it::class.java == clazz }

        throw IndexOutOfBoundsException(
                String.format("%s is out of your registered adapter'(%s) bounds.",
                        clazz.name, adapters.toString())
        )
    }

    companion object Factory {
        fun <T> wrap(classLinker: ClassLinker<T>, adapters: List<Class<out MultiAdapter<*, *>>>): ClassLinkerWrapper<T> {
            return ClassLinkerWrapper(classLinker, adapters)
        }
    }
}