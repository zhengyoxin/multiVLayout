package com.yoxin.multivlayout

/**
 * Created by zhengyongxin on 2018/2/25.
 * 类型分发池
 */
interface TypePool {

    /**
     * Registers a type class and its item view adapter.
     *
     * @param clazz the class of a item
     * @param adapter the item view adapter
     * @param linker the linker to link the class and view adapter
     * @param <T> the item data type
     */
    fun register(
            clazz: Class<*>,
            adapter: Class<out MultiAdapter<*, *>>,
            linker: Linker<*>)

    /**
     * Unregister all items with the specified class.
     *
     * @param clazz the class of items
     * @return true if any items are unregistered from the pool
     */
    fun unregister(clazz: Class<*>): Boolean

    /**
     * Returns the number of items in this pool.
     *
     * @return the number of items in this pool
     */
    fun size(): Int

    /**
     * For getting index of the item class. If the subclass is already registered,
     * the registered mapping is used. If the subclass is not registered, then look
     * for its parent class if is registered, if the parent class is registered,
     * the subclass is regarded as the parent class.
     *
     * @param clazz the item class.
     * @return The index of the first occurrence of the specified class
     * in this pool, or -1 if this pool does not contain the class.
     */
    fun firstIndexOf(clazz: Class<*>): Int

    /**
     * Gets the class at the specified index.
     *
     * @param index the item index
     * @return the class at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    fun getClass(index: Int): Class<*>

    /**
     * Gets the item view adapter at the specified index.
     *
     * @param index the item index
     * @return the item class at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    fun getItemViewAdapter(index: Int): Class<out MultiAdapter<*, *>>

    /**
     * Gets the linker at the specified index.
     *
     * @param index the item index
     * @return the linker at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    fun getLinker(index: Int): Linker<*>
}