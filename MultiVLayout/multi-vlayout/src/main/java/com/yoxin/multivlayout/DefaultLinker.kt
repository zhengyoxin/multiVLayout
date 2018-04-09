package com.yoxin.multivlayout

/**
 * Created by zhengyongxin on 2018/2/25.
 */
class DefaultLinker<T> : Linker<T> {
    override fun index(t: T): Int {
        return 0
    }
}