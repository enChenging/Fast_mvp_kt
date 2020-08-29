package com.release.fast.ui.page.home4_page


import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.mvp.contract.Home4PageContract
import com.release.fast.mvp.presenter.Home4PagePresenter

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class Home4Page : BFragment<Home4PageContract.View, Home4PageContract.Presenter>(),
    Home4PageContract.View {

    override fun createPresenter(): Home4PageContract.Presenter = Home4PagePresenter()

    override fun getLayoutId(): Int = R.layout.page_kinds
}
