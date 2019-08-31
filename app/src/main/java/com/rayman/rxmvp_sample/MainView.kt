package com.rayman.rxmvp_sample

import com.rayman.rxmvp.base.IView

/**
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-31
 */
interface MainView : IView {
    fun showData(data: String)
}