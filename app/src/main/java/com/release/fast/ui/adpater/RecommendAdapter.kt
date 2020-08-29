package com.release.fast.ui.adpater

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.release.fast.R
import com.release.fast.constant.Constant
import com.release.fast.mvp.model.Row
import com.release.easybasex.utils.DefIconFactory
import com.release.easybasex.utils.ImageLoader

/**
 * @author Mr.release
 * @create 2019/3/22
 * @Describe
 */
class RecommendAdapter(layoutResId: Int, data: MutableList<Row>?) :
    BaseQuickAdapter<Row, BaseViewHolder>(layoutResId, data) {

    override fun convert(helper: BaseViewHolder, item: Row) {

        ImageLoader.loadFitCenter(
            context,
            Constant.TEST_IMAGE_URL,
            helper.getView(R.id.iv_tuijian),
            DefIconFactory.provideIcon()
        )
        helper.setText(R.id.tv_tuijian_title, item.noticeTitle)
        helper.setText(R.id.tv_tuijian_time, item.noticeCreateTime)
    }

    override fun setOnItemChildClick(v: View, position: Int) {
        super.setOnItemChildClick(v, position)
    }
}
