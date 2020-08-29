package com.release.fast.mvp.presenter

import com.release.fast.constant.Constant
import com.release.fast.ext.ext
import com.release.fast.http.RetrofitHelper
import com.release.fast.mvp.contract.Home3PageContract
import com.release.easybasex.base.BasePresenter

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */

class Home3PagePresenter : BasePresenter<Home3PageContract.View>(),
    Home3PageContract.Presenter {

    override fun requestData(
        pageNum: Int,
        userId: String,
        noticeType: Int,
        isRefresh: Boolean,
        isShowLoading: Boolean
    ) {

        RetrofitHelper.recommendService
            .getRecommendData(pageNum, Constant.PAGE, userId, noticeType, 0)
            .ext(mView, scopeProvider!!, isShowLoading) {
                mView?.loadData(it.obj,isRefresh)
            }
    }
}



