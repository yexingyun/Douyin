package com.yxe.ktarmor.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yxe.ktarmor.R

/**
 * author：  HyZhan
 * create：  2019/6/24
 * desc：    TODO
 */
class ArticleAdapter: BaseQuickAdapter<String, BaseViewHolder>(R.layout.article_item, null){
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.setText(R.id.tvTitle, item ?: "1")
    }
}