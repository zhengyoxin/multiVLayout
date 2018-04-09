package com.yoxin.multivlayout

import java.lang.RuntimeException

/**
 * Created by zhengyongxin on 2018/2/25.
 * Adapter Not Found Exception
 */
internal class AdapterNotFoundException(clazz: Class<*>) : RuntimeException("Do you have registered the binder for {className}.class in the adapter/pool?"
        .replace("{className}", clazz.simpleName))