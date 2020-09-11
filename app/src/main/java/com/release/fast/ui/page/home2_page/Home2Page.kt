package com.release.fast.ui.page.home2_page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.orhanobut.logger.Logger
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.mvp.contract.Home2PageContract
import com.release.fast.mvp.presenter.Home2PagePresenter
import com.release.easybasex.base.ViewPagerAdapter
import com.release.fast.ui.page.home1_page.Home1Page
import kotlinx.android.synthetic.main.page_video.*
import java.util.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class Home2Page : BFragment<Home2PageContract.View, Home2PageContract.Presenter>(),
    Home2PageContract.View {
    private val VIDEO_ID = arrayOf("V9LG4B3A0", "V9LG4E6VR", "V9LG4CHOR", "00850FRB")
    private val VIDEO_TITLE = arrayOf("热点", "搞笑", "娱乐", "精品")
    private var fragments: MutableList<Fragment> = ArrayList()
    private val mAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(childFragmentManager)
    }

    override fun createPresenter(): Home2PageContract.Presenter = Home2PagePresenter()

    override fun getLayoutId(): Int = R.layout.page_video

    companion object {

        fun newInstance(): Home2Page {
            val args = Bundle()
            val fragment = Home2Page()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(view: View) {
        Logger.i("Home2Page---initView")
        view_pager.adapter = mAdapter
        fragments.clear()
        for (i in VIDEO_ID.indices) {
            fragments.add(VideoListFragment.newInstance(VIDEO_ID[i]))
        }
        mAdapter.setItems(fragments, VIDEO_TITLE)
        stl_tab_layout.setViewPager(view_pager, VIDEO_TITLE)
    }


}
