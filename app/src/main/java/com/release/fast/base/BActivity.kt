package com.release.fast.base

import com.release.easybasex.base.BaseMvpActivity
import com.release.easybasex.base.IPresenter
import com.release.easybasex.base.IView

/**
 * @author Mr.release
 * @create 2020/8/28
 * @Describe
 */

abstract class BActivity<V : IView, P : IPresenter<V>> : BaseMvpActivity<V, P>() {
    override fun initView() {
        super.initView()
    }

}