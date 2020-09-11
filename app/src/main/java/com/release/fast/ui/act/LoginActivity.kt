package com.release.fast.ui.act

import android.content.Intent
import android.graphics.Color
import android.view.View
import com.just.agentweb.AgentWeb
import com.qmuiteam.qmui.kotlin.onClick
import com.release.alert.Alert
import com.release.easybasex.base.BaseActivity
import com.release.easybasex.utils.StatusBarUtil
import com.release.fast.MainActivity
import com.release.fast.R
import com.release.fast.utils.CommonUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * @author Mr.release
 * @create 2020/9/10
 * @Describe
 */
class LoginActivity : BaseActivity() {

    private var alert: Alert? = null

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun isOriginalLayout(): Boolean {
        return true
    }

    override fun initView() {
        alert = Alert(this)
    }

    override fun initListener() {
        et_username.run {
            setOnFocusChangeListener { v, hasFocus ->
                isSelected = hasFocus
            }
        }
        et_password.let {
            it.setOnFocusChangeListener { v, hasFocus ->
                it.isSelected = hasFocus
            }
        }

        btn_login.apply {
            onClick {
                alert?.builder(Alert.Type.PROGRESS)?.setProgressText("登陆中...")?.show()
                postDelayed(Runnable {
                    jump()
                },2000)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        alert?.dissmiss()
    }

    private fun jump() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    override fun initThemeColor() {
       CommonUtil.hideStatusBar(this)
    }
}