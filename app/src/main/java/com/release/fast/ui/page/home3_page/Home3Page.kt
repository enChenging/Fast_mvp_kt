package com.release.fast.ui.page.home3_page

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.orhanobut.logger.Logger
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.constant.Constant
import com.release.fast.http.api.BaseURL
import com.release.fast.mvp.contract.Home3PageContract
import com.release.fast.mvp.model.Obj
import com.release.fast.mvp.model.Row
import com.release.fast.mvp.presenter.Home3PagePresenter
import com.release.fast.ui.act.WebActivity
import com.release.fast.ui.adpater.RecommendAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import kotlinx.android.synthetic.main.page_recommend.*

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class Home3Page :
    BFragment<Home3PageContract.View, Home3PageContract.Presenter>(),
    Home3PageContract.View {

    var mUrl = ""

    override fun createPresenter(): Home3PageContract.Presenter = Home3PagePresenter()

    override fun getLayoutId(): Int = R.layout.page_recommend

    private val mAdapter: RecommendAdapter by lazy {
        RecommendAdapter(R.layout.item_recommend, null)
    }

    companion object {

        fun newInstance(): Home3Page {
            val args = Bundle()
            val fragment = Home3Page()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView(view: View) {
        Logger.i("Home3Page---initView")

        refresh_layout.run {
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    finishLoadMore(1000)
                    val page = mAdapter.data.size / Constant.PAGE
                    Logger.i("page====$page")
                    mPresenter?.requestData(
                        page, "7638", 1,
                        isRefresh = false,
                        isShowLoading = false
                    )
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    finishRefresh(1000)
                    mPresenter?.requestData(1, "7638", 1, isRefresh = true, isShowLoading = false)
                }
            })
        }

        rv_list.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = mAdapter
        }

        mAdapter.run {
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInLeft)
            setOnItemClickListener { adapter, view, position ->
                val bean = adapter.data[position] as Row

                val options: ActivityOptions = ActivityOptions.makeScaleUpAnimation(
                    view,
                    view.width / 2,
                    view.height / 2,
                    0,
                    0
                )
                val url: String =
                    BaseURL.RECOMMEND_HOST + mUrl.subSequence(
                        1,
                        mUrl.length
                    ) + "?noticeId=" + bean.noticeId + "&userId=7638"
                Intent(context, WebActivity::class.java).run {
                    putExtra(Constant.CONTENT_URL_KEY, url)
                    putExtra(Constant.CONTENT_TITLE_KEY, bean.noticeTitle)
                    context?.startActivity(this, options.toBundle())
                }
            }
        }
    }

    override fun startNet() {
        mPresenter?.requestData(1, "7638", 1, isRefresh = false, isShowLoading = true)
    }


    override fun loadData(data: Obj, isRefresh: Boolean) {
        mUrl = data.url
        mAdapter.run {
            if (isRefresh)
                setList(data.items.rows)
            else
                data.items.rows.let { addData(it) }
        }
    }


}
