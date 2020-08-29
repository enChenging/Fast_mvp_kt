package com.release.fast.ui.page.home1_page

import android.view.View
import androidx.fragment.app.Fragment
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.mvp.contract.Home1PageContract
import com.release.fast.mvp.model.NewsInfoBean
import com.release.fast.mvp.presenter.Home1PagePresenter
import com.release.easybasex.base.ViewPagerAdapter
import kotlinx.android.synthetic.main.page_news.*
import java.util.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class Home1Page : BFragment<Home1PageContract.View, Home1PageContract.Presenter>(),
    Home1PageContract.View {

    private val TITLE = arrayOf("item1", "item2", "item3", "item4")
    private var fragments: MutableList<Fragment> = ArrayList()
    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(childFragmentManager)
    }

    override fun createPresenter(): Home1PageContract.Presenter = Home1PagePresenter()

    override fun getLayoutId(): Int = R.layout.page_news

    override fun initView(view: View) {
        super.initView(view)
        view_pager.adapter = mAdapter
        for (i in TITLE.indices) {
            fragments.add(NewsListFragment.newInstance(TITLE[i]))
        }
        mAdapter.setItems(fragments, TITLE)
        stl_tab_layout.setViewPager(view_pager, TITLE)
    }

    override fun startNet() {
        mPresenter?.requestData()
    }

    override fun loadData(data: NewsInfoBean) {
        TODO("Not yet implemented")
    }
}
