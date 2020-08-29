package com.release.fast.base

import android.view.View
import com.release.easybasex.base.BaseMvpFragment
import com.release.easybasex.base.IPresenter
import com.release.easybasex.base.IView

/**
 * @author Mr.release
 * @create 2020/8/28
 * @Describe
 */

abstract class BFragment<V : IView, P : IPresenter<V>> : BaseMvpFragment<V, P>() {

    override fun initView(view: View) {
        super.initView(view)
    }

}