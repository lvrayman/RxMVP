package com.rayman.rxmvp.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import io.reactivex.disposables.Disposable

/**
 * P层基类，继承LifecycleObserver，可在Presenter中监听到生命周期并且进行处理
 *
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-28
 */
interface IPresenter<out V : IView> : LifecycleObserver {
    val view: V

    fun onCreate()
    fun onResume()
    fun onStart()
    fun onPause()
    fun onStop()
    fun onDestroy()
    fun addDisposable(disposable: Disposable)
    fun getCurrentDisposable(): Disposable?

    abstract class BasePresenter<out V : IView> protected constructor(
        override val view: V,
        lifecycle: Lifecycle
    ) : IPresenter<V> {
        protected val disposableList = arrayListOf<Disposable>()
        var disposableCount = 10

        init {
            lifecycle.addObserver(this)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        private fun onCreate(owner: LifecycleOwner) {
            onCreate()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        private fun onStart(owner: LifecycleOwner) {
            onStart()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        private fun onResume(owner: LifecycleOwner) {
            onResume()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
        private fun onPause(owner: LifecycleOwner) {
            onPause()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        private fun onStop(owner: LifecycleOwner) {
            onStop()
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        private fun onDestroy(owner: LifecycleOwner) {
            onDestroy()
        }

        override fun onCreate() {
        }

        override fun onResume() {
        }

        override fun onStart() {
        }

        override fun onPause() {
        }

        override fun onStop() {
        }

        override fun onDestroy() {
            disposableList.forEach { it.dispose() }
        }

        override fun addDisposable(disposable: Disposable) {
            if (disposableList.size >= disposableCount) {
                val first = disposableList.first()
                first.dispose()
                disposableList.remove(first)
            }
            disposableList.add(disposable)
        }

        override fun getCurrentDisposable(): Disposable? = disposableList.lastOrNull()
    }
}