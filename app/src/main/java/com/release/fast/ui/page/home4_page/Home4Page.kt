package com.release.fast.ui.page.home4_page


import android.os.Bundle
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.mvp.contract.Home4PageContract
import com.release.fast.mvp.presenter.Home4PagePresenter
import com.release.fast.ui.page.home3_page.Home3Page

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class Home4Page : BFragment<Home4PageContract.View, Home4PageContract.Presenter>(),
    Home4PageContract.View {

    override fun createPresenter(): Home4PageContract.Presenter = Home4PagePresenter()

    override fun getLayoutId(): Int = R.layout.page_kinds

    companion object {

        fun newInstance(): Home4Page {
            val args = Bundle()
            val fragment = Home4Page()
            fragment.arguments = args
            return fragment
        }
    }
}
