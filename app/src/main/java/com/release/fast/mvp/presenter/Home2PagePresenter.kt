package com.release.fast.mvp.presenter

import com.release.fast.constant.Constant
import com.release.fast.ext.ext
import com.release.fast.http.RetrofitHelper
import com.release.fast.mvp.contract.Home2PageContract
import com.release.fast.mvp.contract.VideoListContract
import com.release.fast.mvp.model.VideoInfoBean
import com.release.easybasex.base.BasePresenter
import io.reactivex.Observable

/**
 * @author Mr.release
 * @create 2019/7/11
 * @Describe
 */
class Home2PagePresenter : BasePresenter<Home2PageContract.View>(), Home2PageContract.Presenter{

}

class VideoListPresenter : BasePresenter<VideoListContract.View>(),
    VideoListContract.Presenter {

    override fun requestData(videoId: String, page: Int, isRefresh: Boolean,isShowLoading: Boolean) {

        RetrofitHelper.newsService.getVideoList(videoId, page * Constant.PAGE_TEN, Constant.PAGE_TEN)
            .flatMap { stringListMap -> Observable.just<List<VideoInfoBean>>(stringListMap[videoId]) }
            .ext(mView, scopeProvider!!, page == 0 && isShowLoading) {
                mView?.loadData(it,isRefresh)
            }
    }
}