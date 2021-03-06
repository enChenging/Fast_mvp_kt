package com.release.fast.mvp.contract

import com.release.fast.mvp.model.NewsInfoBean
import com.release.fast.mvp.model.Obj
import com.release.easybasex.base.IPresenter
import com.release.easybasex.base.IView

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface Home1PageContract {

    interface View : IView {
        fun loadData(data : NewsInfoBean)
    }

    interface Presenter : IPresenter<View> {
        fun requestData()
    }
}


interface NewsListContract {

    interface View : IView {
        fun loadData(data: Obj,isRefresh: Boolean,isShowLoading: Boolean)
    }

    interface Presenter : IPresenter<View> {
        fun requestData(
            pageNum: Int,
            userId: String,
            noticeType: Int,
            isRefresh: Boolean,
            isShowLoading: Boolean
        )
    }
}