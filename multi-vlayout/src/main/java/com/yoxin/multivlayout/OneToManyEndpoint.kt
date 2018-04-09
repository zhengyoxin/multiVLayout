package com.yoxin.multivlayout

/**
 * Created by zhengyongxin on 2018/2/26.
 * 一对多构建器最后的操作步骤
 */
interface OneToManyEndpoint<T> {

    /**
     * 设置一个整型连接器，连接数据类对应的不同adapter的下标
     * @see [Linker]
     */
    fun withLink(linker: Linker<T>)

    /**
     * 设置一个类连接器，连接数据类对应的不同adapter
     * @see [ClassLinker]
     */
    fun withLink(classLinker: ClassLinker<T>)
}