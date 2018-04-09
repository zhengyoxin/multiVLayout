package com.yoxin.multivlayout

/**
 * Created by zhengyongxin on 2018/2/25.
 * An List implementation of TypePool.
 */
class MultiTypePool(
        private val classes: MutableList<Class<*>> = mutableListOf(),
        private val adapters: MutableList<Class<out MultiAdapter<*, *>>> = mutableListOf(),
        private val linkers: MutableList<Linker<*>> = mutableListOf())
    : TypePool {
    override fun register(clazz: Class<*>, adapter: Class<out MultiAdapter<*, *>>, linker: Linker<*>) {
        classes.add(clazz)
        adapters.add(adapter)
        linkers.add(linker)
    }

    override fun unregister(clazz: Class<*>): Boolean {
        var removed = false
        while (true) {
            val index = classes.indexOf(clazz)
            if (index != -1) {
                classes.removeAt(index)
                adapters.removeAt(index)
                linkers.removeAt(index)
                removed = true
            } else {
                break
            }
        }
        return removed
    }

    override fun size(): Int {
        return classes.size
    }

    override fun firstIndexOf(clazz: Class<*>): Int {
        val index = classes.indexOf(clazz)
        if (index != -1) {
            return index
        }

//        classes.indices.forEach {
//            classes[it].isAssignableFrom(clazz)
//            return it
//        }
        return -1
    }

    override fun getClass(index: Int): Class<*> {
        return classes[index]
    }

    override fun getItemViewAdapter(index: Int): Class<out MultiAdapter<*, *>> {
        return adapters[index]
    }

    override fun getLinker(index: Int): Linker<*> {
        return linkers[index]
    }

}