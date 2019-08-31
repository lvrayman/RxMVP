package com.rayman.rxmvp_sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author 吕少锐 (lvrayman@gmail.com)
 * @version 2019-08-31
 */
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
        tvData.text = data
    }
}
