package com.release.fast.ui.adpater

import cn.jzvd.Jzvd
import cn.jzvd.JzvdStd
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.release.fast.R
import com.release.fast.mvp.model.VideoInfoBean
import com.release.easybasex.utils.DefIconFactory
import com.release.easybasex.utils.ImageLoader

/**
 * @author Mr.release
 * @create 2019/4/16
 * @Describe
 */
class VideoListAdapter(layoutResId: Int, data: MutableList<VideoInfoBean>?) :
    BaseQuickAdapter<VideoInfoBean, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: VideoInfoBean) {

        val videoplayer = holder.getView<JzvdStd>(R.id.videoplayer)

        holder.setText(R.id.tv_title, item.title)

        videoplayer.setUp(item.mp4_url, item.title, Jzvd.SCREEN_NORMAL)

        ImageLoader.loadFitCenter(
            context,
            item.cover,
            videoplayer.thumbImageView,
            DefIconFactory.provideIcon()
        )
    }
}
