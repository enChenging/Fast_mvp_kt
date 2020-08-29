package com.release.fast.mvp.contract

import com.release.fast.mvp.model.VideoInfoBean
import com.release.easybasex.base.IPresenter
import com.release.easybasex.base.IView

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
interface Home2PageContract {

    interface View:IView

    interface Presenter:IPresenter<View>
}

interface VideoListContract {

    interface View : IView {

        fun loadData(data: List<VideoInfoBean>, isRefresh :Boolean)
    }

    interface Presenter : IPresenter<View> {

        fun requestData(videoId: String, page: Int,isRefresh :Boolean,isShowLoading: Boolean)
    }
}