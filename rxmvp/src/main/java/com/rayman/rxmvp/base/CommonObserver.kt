package com.rayman.rxmvp.base

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-28
 */
abstract class CommonObserver<T> protected constructor(private val presenter: IPresenter<IView>) :
    Observer<T> {

    override fun onSubscribe(d: Disposable) {
        presenter.addDisposable(d)
    }

    override fun onComplete() {
    }

    override fun onError(t: Throwable) {
        t.printStackTrace()
    }
}