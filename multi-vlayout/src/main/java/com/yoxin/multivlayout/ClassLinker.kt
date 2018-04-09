package com.yoxin.multivlayout

import javax.annotation.Nonnull

/**
 * Created by zhengyongxin on 2018/2/26.
 * 一个通过adapter类下标来连接数据类和adapter
 */
interface ClassLinker<T> {

    /**
     * 返回一个你注册的adapters的其中一个
     *
     * @param t 你的数据
     * @return 相同数据类型不同数据要返回的adapter类名，返回的adapter必须是[OneToManyFlow].to[MultiAdapter]中注册了的
     */
    @Nonnull
    fun index(@Nonnull t: T): Class<out MultiAdapter<T, *>>
}