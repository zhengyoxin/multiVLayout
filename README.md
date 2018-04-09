# MultiVLayout

一个简单容易创建多视图列表的Android开源库

以前，当我们需要开发一个复杂的RecyclerView / ListView时，这是一件困难而麻烦的工作。我们应该重写RecyclerView.Adapter的getItemViewType（），添加一些类型，并创建一些与这些类型相关的ViewHolders。

一旦我们需要添加新的项目类型，我们必须转到原始的适配器文件并仔细修改一些旧代码，这些适配器类将变得更加复杂。

现在，创建了一种比较的直观和灵活的方式来轻松创建复杂的RecyclerView，使用MultiVLayout库，我们可以插入新的项目类型，而无需更改任何旧的适配器代码并使其更具可读性。

## 使用指南 ##
在你的`build.gradle`:
```groovy
dependencies {
    implementation 'com.yoxin.multivlayout:multivlayout:1.0.0'
}
```

## 使用方法
### Step 1：创建你的数据模型（Java Bean），例如：
```kotlin
data class TextData(var text: String)
```

### Step 2：创建一个类继承MultiAdapter<T, VH : RecyclerView.ViewHolder?>，例如：
```kotlin
class TextAdapter : MultiAdapter<TextData, RecyclerView.ViewHolder>() {
    override fun onCreateLayoutHelper(): LayoutHelper {
        return SingleLayoutHelper()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.text.text = getData().text
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.text, null)
        return TextVH(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun setItemViewType(position: Int): Int {
        return 1
    }

    class TextVH(view: View) : RecyclerView.ViewHolder(view)
}
```
### Step 3：只需register您的数据类型和对应的Adapter并在您的Activity中设置RecyclerView和List <Object>，例如：

```kotlin
class MainActivity : AppCompatActivity() {

    lateinit var multiDelegateAdapter: MultiDelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        main_container.apply {
            val vManager = VirtualLayoutManager(this@MainActivity)
            layoutManager = vManager
            multiDelegateAdapter = MultiDelegateAdapter(this@MainActivity, vManager).apply {
                register(TextData::class.java, TextAdapter::class.java)
                register(ImageData::class.java, ImgAdapter::class.java)
                register(RichData::class.java, RichAdapter::class.java)
            }
            val delegateAdapter = multiDelegateAdapter
            this.adapter = delegateAdapter
        }

        val data = mutableListOf<Any>()
        (0 .. 10).forEach {
            data.add(TextData("$it"))
            data.add(ImageData(R.drawable.ic_launcher_foreground))
            data.add(RichData("$it", R.drawable.ic_launcher_background))
        }

        multiDelegateAdapter.setData(data)
    }

}
```

## MultiAdapter的使用
`onCreateViewHolder`和`onBindViewHolder `和Recycler.Adatper的接口作用一致
`getItemCount`返回该数据模块的个数
`onCreateLayoutHelper `确定该模块的布局
 * 默认通用布局实现，解耦所有的View和布局之间的关系: Linear, Grid, 吸顶, 浮动, 固定位置等。
	* LinearLayoutHelper: 线性布局
	* GridLayoutHelper:  Grid布局， 支持横向的colspan
	* FixLayoutHelper: 固定布局，始终在屏幕固定位置显示
	* ScrollFixLayoutHelper: 固定布局，但之后当页面滑动到该图片区域才显示, 可以用来做返回顶部或其他书签等
	* FloatLayoutHelper: 浮动布局，可以固定显示在屏幕上，但用户可以拖拽其位置
	* ColumnLayoutHelper: 栏格布局，都布局在一排，可以配置不同列之间的宽度比值
	* SingleLayoutHelper: 通栏布局，只会显示一个组件View
	* OnePlusNLayoutHelper: 一拖N布局，可以配置1-5个子元素
	* StickyLayoutHelper: stikcy布局， 可以配置吸顶或者吸底
	* StaggeredGridLayoutHelper: 瀑布流布局，可配置间隔高度/宽度
 * 上述默认实现里可以大致分为两类：一是非fix类型布局，像线性、Grid、栏格等，它们的特点是布局在整个页面流里，随页面滚动而滚动；另一类就是fix类型的布局，它们的子节点往往不随页面滚动而滚动。
 * 所有除布局外的组件复用，VirtualLayout将用来管理大的模块布局组合，扩展了RecyclerView，使得同一RecyclerView内的组件可以复用，减少View的创建和销毁过程。
* 不同的LayoutHelper的实际效果可以参考[vlayout](https://github.com/alibaba/vlayout)的demo

## 高级用法
### 一对多
``` kotlin
adapter.register(Data::class.java).to(
     DataType1MultiAdapter::class.java,
     DataType2MultiAdapter::class.java
).withLinker(object: Linker<TextData> {
     override fun index(t: TextData): Int {
          return data.type == Data.TYPE_2 ? 1 : 0
     }
})
```
或者
```kotlin
adapter.register(Data::class.java).to(
    DataType1MultiAdapter::class.java,
    DataType2MultiAdapter::class.java
).withLinker(object:  ClassLinker<TextData> {
    override fun index(t: TextData): Class<out MultiAdapter<TextData, *>> {
        if (data.type == Data.TYPE_2) {
            return DataType2MultiAdapter::class.java
        } else {
            return DataType1MultiAdapter::class.java
        }
    }
})
```
## 感谢
[MultiType](https://github.com/drakeet/MultiType)
[vlayout](https://github.com/alibaba/vlayout)
