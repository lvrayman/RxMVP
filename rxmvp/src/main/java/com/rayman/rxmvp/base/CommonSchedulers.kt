package com.rayman.rxmvp.base

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 通用线程调度的封装
 *
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-31
 */
object CommonSchedulers {
    fun <T> io2main(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    }
}
