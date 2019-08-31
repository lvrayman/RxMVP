package com.rayman.rxmvp_sample

import androidx.lifecycle.Lifecycle
import com.rayman.rxmvp.base.CommonObserver
import com.rayman.rxmvp.base.CommonSchedulers
import com.rayman.rxmvp.base.IPresenter
import io.reactivex.Observable

/**
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-31
 */
interface MainPresenter : IPresenter<MainView> {
    fun getData()

    class Impl(view: MainView, lifecycle: Lifecycle) :
        IPresenter.BasePresenter<MainView>(view, lifecycle), MainPresenter {

        override fun getData() {
            Observable.create<String> {
                it.onNext("Now you got data")
            }.compose(CommonSchedulers.io2main())
                .subscribe(object : CommonObserver<String>(this) {
                    override fun onNext(t: String) {
                        view.showData(t)
                    }
                })
        }
    }
}