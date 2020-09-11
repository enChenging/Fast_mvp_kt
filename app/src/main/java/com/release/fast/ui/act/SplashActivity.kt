package com.release.fast.ui.act

import android.Manifest
import android.content.Intent
import android.os.Build
import android.view.View
import com.release.alert.Alert
import com.release.fast.MainActivity
import com.release.fast.R
import com.release.fast.widget.InstallUtil
import com.release.fast.widget.NoticeDialog
import com.release.easybasex.base.BaseActivity
import com.release.easybasex.utils.StatusBarUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDisposable
import kotlinx.android.synthetic.main.activity_splash.*

/**
 * @author Mr.release
 * @create 2019/8/5
 * @Describe
 */
class SplashActivity : BaseActivity() {

    private var hasPermission: Boolean = false
    private var isNever: Boolean = false

    private val scopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    override fun isOriginalLayout(): Boolean {
        return true
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initView() {
        if (Build.VERSION.SDK_INT >= 23)
            requestCameraPermissions()
        else
            jump()
    }

    override fun initListener() {
        btn_permission.run {
            setOnClickListener {
                if (Build.VERSION.SDK_INT >= 23)
                    requestCameraPermissions()
            }
        }
    }


    private fun jump() {
        Intent(this, LoginActivity::class.java).run {
            startActivity(this)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (isNever) {
            if (Build.VERSION.SDK_INT >= 23)
                requestCameraPermissions()
        }
    }

    /**
     * 请求权限
     */
    private fun requestCameraPermissions() {
        RxPermissions(this).requestEachCombined(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
            .autoDisposable(scopeProvider)
            .subscribe { permission ->
                when {
                    permission.granted -> {
                        hasPermission = true
                        jump()
                    }
                    permission.shouldShowRequestPermissionRationale ->
                        showNotice(resources.getString(R.string.rationale_wr))
                    else -> {
                        isNever = true
                        showNotice(resources.getString(R.string.rationale_ask_again))
                    }
                }
            }

    }

    private fun showNotice(content: String) {
        NoticeDialog.show(this, content, View.OnClickListener {
            when (it.id) {
                R.id.iv_close, R.id.tv_cancel -> btn_permission.visibility = View.VISIBLE
                R.id.tv_ok -> if (isNever)
                    InstallUtil.startAppSettings(this)
            }
            NoticeDialog.dismissDialog()
        }, false)
    }

    override fun initThemeColor() {
        StatusBarUtil.setTranslucent(this, 0)
    }
}