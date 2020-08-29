package com.release.fast.mvp.contract

import com.release.easybasex.base.IPresenter
import com.release.easybasex.base.IView


/**
 * @author Mr.release
 * @create 2019/6/26
 * @Describe
 */
interface Home4PageContract {

    interface View : IView

    interface Presenter : IPresenter<View>
}