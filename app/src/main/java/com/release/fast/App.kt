package com.release.fast

import android.app.Application
import android.content.Context
import com.release.fast.constant.Constant
import com.release.fast.ext.showToast
import com.release.fast.utils.CommonUtil
import com.release.fast.utils.DisplayManager
import com.release.easybasex.base.BaseApplication
import com.release.itoolbar.IToolBar
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.upgrade.UpgradeStateListener
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

/**
 * @author Mr.release
 * @create 2019/7/10
 * @Describe
 */
class App : BaseApplication() {

    companion object {
        var context: Context by Delegates.notNull()
        lateinit var instance: Application

        init {
            SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.white, android.R.color.black)//全局设置主题颜色
                ClassicsHeader(context)
            }
            SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
                layout.setPrimaryColorsId(R.color.white, android.R.color.black)
                ClassicsFooter(context).setDrawableSize(20f)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext
    }

    /**
     * 初始化配置
     */
    override fun initConfig() {
        super.initConfig()
        IToolBar.init(R.color.theme_color,R.color.White,R.mipmap.toolbar_back_white);
        DisplayManager.init(this)
        initBugly()
    }

    /**
     * 初始化 Bugly
     */
    private fun initBugly() {
        if (BuildConfig.DEBUG) {
            return
        }
        // 获取当前包名
        val packageName = applicationContext.packageName
        // 获取当前进程名
        val processName = CommonUtil.getProcessName(android.os.Process.myPid())
        Beta.upgradeStateListener = object : UpgradeStateListener {
            override fun onDownloadCompleted(isManual: Boolean) {
            }

            override fun onUpgradeSuccess(isManual: Boolean) {
            }

            override fun onUpgradeFailed(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_version_fail))
                }
            }

            override fun onUpgrading(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_version_ing))
                }
            }

            override fun onUpgradeNoVersion(isManual: Boolean) {
                if (isManual) {
                    showToast(getString(R.string.check_no_version))
                }
            }
        }
        // 设置是否为上报进程
        val strategy = CrashReport.UserStrategy(applicationContext)
        strategy.isUploadProcess = false || processName == packageName
        Bugly.init(applicationContext, Constant.BUGLY_ID, BuildConfig.DEBUG, strategy)
    }

}
