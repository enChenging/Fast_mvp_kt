package com.release.fast.ui.activity

import android.text.TextUtils
import com.release.fast.base.BWebActivity
import com.release.fast.constant.Constant

/**
 * @author Mr.release
 * @create 2020/8/27
 * @Describe
 */

class WebActivity : BWebActivity() {

    private lateinit var shareTitle: String
    private lateinit var shareUrl: String

    override fun initView() {
        intent.extras?.let {
            shareTitle = it.getString(Constant.CONTENT_TITLE_KEY, "")
            shareUrl = it.getString(Constant.CONTENT_URL_KEY, "")
        }
        super.initView()
    }

    override fun getWebUrl(): String? {
        return shareUrl.apply {
            if (TextUtils.isEmpty(this))
                this@WebActivity.finish()
            else
                return this
        }
    }

    override fun getWebTitle(): String? {
        return shareTitle
    }
}