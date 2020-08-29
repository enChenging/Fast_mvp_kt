package com.release.fast.ui.page.home2_page

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.Jzvd
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.constant.Constant
import com.release.fast.mvp.contract.VideoListContract
import com.release.fast.mvp.model.VideoInfoBean
import com.release.fast.mvp.presenter.VideoListPresenter
import com.release.fast.ui.adpater.VideoListAdapter
import com.release.easybasex.constance.BConstants.VIDEO_ID_KEY
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.fragment_video_list.*
import kotlinx.android.synthetic.main.page_recommend.refresh_layout

/**
 * @author Mr.release
 * @create 2019/4/16
 * @Describe
 */
class VideoListFragment : BFragment<VideoListContract.View, VideoListContract.Presenter>(),
    VideoListContract.View {

    companion object {

        fun newInstance(videoId: String): VideoListFragment {
            val fragment = VideoListFragment()
            val bundle = Bundle()
            bundle.putString(VIDEO_ID_KEY, videoId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): VideoListContract.Presenter = VideoListPresenter()

    override fun getLayoutId(): Int = R.layout.fragment_video_list

    private lateinit var mVideoId: String

    private val mAdapter: VideoListAdapter by lazy {
        VideoListAdapter(R.layout.adapter_video_list, null)
    }


    override fun initView(view: View) {
        super.initView(view)

        mVideoId = arguments?.getString(VIDEO_ID_KEY).toString()
        refresh_layout.run {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    finishLoadMore(1000)
                    val page = mAdapter.data.size / Constant.PAGE_TEN
                    mPresenter?.requestData(
                        mVideoId, page,
                        isRefresh = false,
                        isShowLoading = false
                    )
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    finishRefresh(1000)
                    mPresenter?.requestData(mVideoId, 0, isRefresh = true, isShowLoading = false)
                }
            })
        }

        rv_photo_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
            addOnChildAttachStateChangeListener(object :
                RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {

                }

                override fun onChildViewDetachedFromWindow(view: View) {
                    val jzvd = view.findViewById<Jzvd>(R.id.videoplayer)
                    if (jzvd != null && Jzvd.CURRENT_JZVD != null &&
                        jzvd.jzDataSource.containsTheUrl(Jzvd.CURRENT_JZVD.jzDataSource.currentUrl)
                    ) {
                        if (Jzvd.CURRENT_JZVD != null && Jzvd.CURRENT_JZVD.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                            Jzvd.resetAllVideos()
                        }
                    }
                }
            })
        }
    }

    override fun startNet() {
        mPresenter?.requestData(mVideoId, 0, isRefresh = false, isShowLoading = true)
    }

    override fun loadData(data: List<VideoInfoBean>, isRefresh: Boolean) {
        mAdapter.run {
            if (isRefresh) {
                setList(data)
            } else {
                addData(data)
            }
        }
    }

}
