# RxMVP使用说明

MVP架构是目前Android开发中最常用的架构，RxJava是目前开发中最常用的异步处理框架，本项目对两者做一个简单的封装，方便快速使用MVP架构进行开发，并优雅地处理生命周期对同异步的处理。

## 使用

在你Project的build.gradle中添加

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

然后在你module的build.gradle中引用

```
dependencies {
    implementation 'com.github.lvrayman:RxMVP:0.0.1'
}
```

以MainActivity为例，需要创建一个MainView和一个MainPresenter

MainView：

```kotlin
interface MainView : IView {
    fun showData(data: String)
}
```

MainPresenter：

```kotlin
interface MainPresenter : IPresenter<MainView> {
    fun getData()

    class Impl(view: MainView, lifecycle: Lifecycle) :
        IPresenter.BasePresenter<MainView>(view, lifecycle), MainPresenter {

        override fun getData() {
            Observable.create<String> {
                it.onNext("Now you got data")
            }.compose(CommonSchedulers.io2main())
                .subscribe(object : CommonObserver<String>(this) {
                  	// 在Presenter中使用RxJava时，使用CommonObserver即可将该RxJava的任务与该Presenter挂钩，自动在生命周期结束时取消订阅
                    override fun onNext(t: String) {
                        view.showData(t)
                    }
                })
        }
    }
}
```

MainActivity实现MainView：

```kotlin
class MainActivity : AppCompatActivity(), MainView {
    private val btnGetData by lazy { btn_get_data }
    private val tvData by lazy { tv_data }

    private val presenter = MainPresenter.Impl(this, lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.disposableCount = 3
        btnGetData.setOnClickListener {
            presenter.getData()
        }
    }

    override fun showData(data: String) {
      	// 实现MainView中的功能
        tvData.text = data
    }
}
```

实现了IPresenter中的BasePresenter监听了生命周期，可在其中通过`onCreate`~`onDestory`对声明周期进行处理，其中`onDestory`的`super`方法不能注释，**否则将无法自动取消订阅**

可以自定义同时进行的RxJava任务数量，默认为10

```kotlin
presenter.disposableCount = 3
```

