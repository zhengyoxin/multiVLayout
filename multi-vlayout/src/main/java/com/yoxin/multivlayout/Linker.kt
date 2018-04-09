package com.yoxin.multivlayout

import android.support.annotation.IntRange

/**
 * Created by zhengyongxin on 2018/2/25.
 * 一个通过整型下标来连接数据类和adapter
 */
interface Linker<T> {

    /**
     * 返回一个你注册的adapter所在的下标，返回值必须大于0
     *
     * adapter来源于 [OneToManyFlow].to[MultiAdapter]
     *
     * @param t 你的数据
     * @return 你注册的adapter的下标
     */
    @IntRange(from = 0)
    fun index(t: T): Int
}