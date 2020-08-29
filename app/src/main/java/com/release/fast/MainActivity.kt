package com.release.fast

import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.release.fast.base.BActivity
import com.release.fast.ext.showToast
import com.release.fast.mvp.contract.MainContract
import com.release.fast.mvp.presenter.MainPresenter
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.release.easybasex.utils.StatusBarUtil
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class MainActivity : BActivity<MainContract.View, MainContract.Presenter>(),
    MainContract.View {


    override fun createPresenter(): MainContract.Presenter = MainPresenter()

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {

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

        bottom_navigation.apply {
            labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        }

        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragment_nav) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    override fun initThemeColor() {
        StatusBarUtil.setColorNoTranslucentForDrawerLayout(
            this@MainActivity,
            dl_drawer,
            ContextCompat.getColor(this, R.color.colorPrimary)
        )
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
    }


    open fun toggle() {
        val drawerLockMode = dl_drawer.getDrawerLockMode(GravityCompat.START)
        if (dl_drawer.isDrawerVisible(GravityCompat.START) && drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN) {
            dl_drawer.closeDrawer(GravityCompat.START)
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            dl_drawer.openDrawer(GravityCompat.START)
        }
    }

    private var mExitTime: Long = 0

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
}


