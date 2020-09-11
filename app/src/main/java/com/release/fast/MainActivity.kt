package com.release.fast

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.release.fast.base.BActivity
import com.release.fast.ext.showToast
import com.release.fast.mvp.contract.MainContract
import com.release.fast.mvp.presenter.MainPresenter
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.orhanobut.logger.Logger
import com.release.easybasex.utils.StatusBarUtil
import com.release.fast.ui.page.home1_page.Home1Page
import com.release.fast.ui.page.home2_page.Home2Page
import com.release.fast.ui.page.home3_page.Home3Page
import com.release.fast.ui.page.home4_page.Home4Page
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_navigation_tab.*
import java.util.ArrayList

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class MainActivity : BActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View, View.OnClickListener {

    companion object {
        private const val TAB_INDEX_HOME: Int = 0x0
        private const val TAB_INDEX_FIND: Int = 0x1
        private const val TAB_INDEX_TASK: Int = 0x2
        private const val TAB_INDEX_MY: Int = 0x3
    }

    private var mExitTime: Long = 0

    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        Logger.i("MainActivity === initView")
        mTopBar.visibility = View.GONE

        left_navigation.run {
            val headImg = getHeaderView(0).findViewById<ImageView>(R.id.headImg)
            Glide.with(this)
                .load("https://b-ssl.duitang.com/uploads/item/201802/20/20180220170028_JcYMU.jpeg")
                .centerCrop()
                .circleCrop()
                .into(headImg)

            setNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.nav_help_center ->
                        Toast.makeText(this@MainActivity, "帮助中心", Toast.LENGTH_SHORT).show()
                    R.id.nav_setting ->
                        Toast.makeText(this@MainActivity, "设置", Toast.LENGTH_SHORT).show()
                    R.id.nav_camera ->
                        Toast.makeText(this@MainActivity, "照相机", Toast.LENGTH_SHORT).show()
                    R.id.nav_gallery ->
                        Toast.makeText(this@MainActivity, "相册", Toast.LENGTH_SHORT).show()
                }
                toggle()
                false
            }
        }

        initFragments()
        setSelectTab(TAB_INDEX_HOME)
        content_vp.apply {
            setScanScroll(false)
            offscreenPageLimit = 4
        }
    }

    private fun initFragments() {
        val fragments = ArrayList<Fragment>()
        fragments.clear()
        fragments.add(Home1Page.newInstance())
        fragments.add(Home2Page.newInstance())
        fragments.add(Home3Page.newInstance())
        fragments.add(Home4Page.newInstance())

        content_vp.adapter = object  : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }
        }

    }

    override fun initListener() {
        dl_drawer.run {
            setScrimColor(ContextCompat.getColor(this@MainActivity, R.color.black_alpha_32))
            addDrawerListener(object : DrawerLayout.DrawerListener {
                override fun onDrawerStateChanged(newState: Int) {

                }

                override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                    dl_drawer.getChildAt(0).translationX = drawerView.width * slideOffset
                }

                override fun onDrawerClosed(drawerView: View) {
                }

                override fun onDrawerOpened(drawerView: View) {
                }

            })
        }

        tab_home_layout.setOnClickListener(this)
        tab_find_layout.setOnClickListener(this)
        tab_task_layout.setOnClickListener(this)
        tab_my_layout.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tab_home_layout -> {
                setSelectTab(TAB_INDEX_HOME)
            }
            R.id.tab_find_layout -> {
                setSelectTab(TAB_INDEX_FIND)
            }
            R.id.tab_task_layout -> {
                setSelectTab(TAB_INDEX_TASK)
            }
            R.id.tab_my_layout -> {
                setSelectTab(TAB_INDEX_MY)
            }
        }
    }

    open fun toggle() {
        val drawerLockMode = dl_drawer.getDrawerLockMode(GravityCompat.START)
        if (dl_drawer.isDrawerVisible(GravityCompat.START) && drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN) {
            dl_drawer.closeDrawer(GravityCompat.START)
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            dl_drawer.openDrawer(GravityCompat.START)
        }
    }

    private fun setSelectTab(tabIndex: Int) {
        val resources = resources
        content_vp.currentItem = tabIndex
        when (tabIndex) {
            TAB_INDEX_HOME -> {
                tab_home_icon_iv.setImageResource(R.mipmap.ic_home_selected)
                tab_find_icon_iv.setImageResource(R.mipmap.ic_find_default)
                tab_task_icon_iv.setImageResource(R.mipmap.ic_task_default)
                tab_my_icon_iv.setImageResource(R.mipmap.ic_my_default)

                tab_home_title_iv.setTextColor(resources.getColor(R.color.tab_text_selected))
                tab_find_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_task_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_my_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))

            }
            TAB_INDEX_FIND -> {
                tab_home_icon_iv.setImageResource(R.mipmap.ic_home_default)
                tab_find_icon_iv.setImageResource(R.mipmap.ic_find_selected)
                tab_task_icon_iv.setImageResource(R.mipmap.ic_task_default)
                tab_my_icon_iv.setImageResource(R.mipmap.ic_my_default)

                tab_home_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_find_title_iv.setTextColor(resources.getColor(R.color.tab_text_selected))
                tab_task_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_my_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
            }
            TAB_INDEX_TASK -> {
                tab_home_icon_iv.setImageResource(R.mipmap.ic_home_default)
                tab_find_icon_iv.setImageResource(R.mipmap.ic_find_default)
                tab_task_icon_iv.setImageResource(R.mipmap.ic_task_selected)
                tab_my_icon_iv.setImageResource(R.mipmap.ic_my_default)

                tab_home_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_find_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_task_title_iv.setTextColor(resources.getColor(R.color.tab_text_selected))
                tab_my_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
            }
            TAB_INDEX_MY -> {
                tab_home_icon_iv.setImageResource(R.mipmap.ic_home_default)
                tab_find_icon_iv.setImageResource(R.mipmap.ic_find_default)
                tab_task_icon_iv.setImageResource(R.mipmap.ic_task_default)
                tab_my_icon_iv.setImageResource(R.mipmap.ic_my_selected)

                tab_home_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_find_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_task_title_iv.setTextColor(resources.getColor(R.color.tab_text_unselected))
                tab_my_title_iv.setTextColor(resources.getColor(R.color.tab_text_selected))
            }
        }
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!closeDrawableLayout()) {
                if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                    finish()
                } else {
                    mExitTime = System.currentTimeMillis()
                    showToast(getString(R.string.exit_tip))
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun closeDrawableLayout(): Boolean {
        return if (dl_drawer.isDrawerVisible(GravityCompat.START)) {
            dl_drawer.closeDrawer(GravityCompat.START)
            true
        } else
            false
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.resetAllVideos()
    }

    override fun initThemeColor() {
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(
            this@MainActivity,
            dl_drawer,
            ContextCompat.getColor(this, R.color.colorPrimary)
        )
    }
}


