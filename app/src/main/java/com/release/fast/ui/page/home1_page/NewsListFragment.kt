package com.release.fast.ui.page.home1_page

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.release.fast.R
import com.release.fast.base.BFragment
import com.release.fast.constant.Constant
import com.release.fast.http.api.BaseURL
import com.release.fast.mvp.contract.NewsListContract
import com.release.fast.mvp.model.Obj
import com.release.fast.mvp.model.Row
import com.release.fast.mvp.presenter.NewsListPresenter
import com.release.fast.ui.act.WebActivity
import com.release.fast.ui.adpater.RecommendAdapter
import com.orhanobut.logger.Logger
import com.release.easybasex.constance.BConstants.NEWS_TYPE_TITLE
import com.release.easybasex.utils.ImageLoader
import kotlinx.android.synthetic.main.page_recommend.*

/**
 * 要闻
 *
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class NewsListFragment : BFragment<NewsListContract.View, NewsListContract.Presenter>(),
    NewsListContract.View {
    private lateinit var newsTitle: String
    private var bannerImagedList = arrayOf(
        Constant.TEST_IMAGE_URL,
        Constant.TEST_IMAGE_URL2,
        Constant.TEST_IMAGE_URL3,
        Constant.TEST_IMAGE_URL4
    )
    private var bannerTitleList = arrayOf("one", "two", "three", "four")
    private var banner: BGABanner? = null
    var mUrl = ""

    companion object {
        fun newInstance(title: String): NewsListFragment {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(NEWS_TYPE_TITLE, title)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun createPresenter(): NewsListContract.Presenter = NewsListPresenter()

    override fun getLayoutId(): Int = R.layout.page_recommend

    private val mAdapter: RecommendAdapter by lazy {
        RecommendAdapter(R.layout.item_recommend, null)
    }

    override fun initView(view: View) {
        super.initView(view)
        newsTitle = arguments?.getString(NEWS_TYPE_TITLE).toString()

        refresh_layout.run {
            setOnRefreshListener {
                finishRefresh(1000)
                mPresenter?.requestData(1, "7638", 1, isRefresh = false, isShowLoading = false)
            }

            setOnLoadMoreListener {
                finishLoadMore(1000)
                val page = mAdapter.data.size / Constant.PAGE
                Logger.i("page====$page")
                mPresenter?.requestData(page, "7638", 1, isRefresh = true, isShowLoading = false)
            }
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

                val url: String =
                    BaseURL.RECOMMEND_HOST + mUrl.subSequence(
                        1,
                        mUrl.length
                    ) + "?noticeId=" + bean.noticeId + "&userId=7638"
                Intent(context, WebActivity::class.java).run {
                    putExtra(Constant.CONTENT_URL_KEY, url)
                    putExtra(Constant.CONTENT_TITLE_KEY, bean.noticeTitle)
                    context?.startActivity(this)
                }
            }
        }

    }

    private fun initBanner() {
        val bannerView = layoutInflater.inflate(R.layout.item_newslist_banner, null)
        if (!mAdapter.hasHeaderLayout())
            mAdapter.addHeaderView(bannerView)
        banner = bannerView.findViewById<BGABanner>(R.id.banner)
        banner?.run {
            setDelegate(bannerDelegate)
            setAdapter(bannerAdapter)
        }

        banner?.setData(
            listOf(*bannerImagedList) as MutableList<String>,
            listOf(*bannerTitleList) as MutableList<String>
        )
    }

    private val bannerAdapter =
        BGABanner.Adapter<ImageView, String> { _, itemView, model, _ ->
            context?.let {
                model?.let { it1 ->
                    ImageLoader.loadCenterCrop(
                        it,
                        it1,
                        itemView,
                        R.mipmap.placeholder_banner
                    )
                }
            }
        }

    /**
     * BannerClickListener
     */
    private val bannerDelegate =
        BGABanner.Delegate<ImageView, String> { _, _, _, position ->

        }

    override fun startNet() {
        mPresenter?.requestData(1, "7638", 1, isRefresh = false, isShowLoading = true)
    }


    override fun loadData(data: Obj, isRefresh: Boolean, isShowLoading: Boolean) {
        mUrl = data.url
        mAdapter.run {
            if (isRefresh || isShowLoading){
                initBanner()
                setList(data.items.rows)
            }
            else
                data.items.rows.let { addData(it) }
        }
    }

}
