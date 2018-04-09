package com.yoxin.multivlayout

import android.support.annotation.CheckResult

/**
 * Created by zhengyongxin on 2018/2/26.
 * 为一对多设置的中间程序
 */
interface OneToManyFlow<T> {
    /**
     * Sets some item view adapters to the item type.
     *
     * @param adapters the item view adapters
     * @return end flow operator
     */
    @CheckResult
    fun to(vararg adapters: Class<out MultiAdapter<T, *>>): OneToManyEndpoint<T>
}