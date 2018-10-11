package com.kermitye.autoscrooldemo

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by kermitye on 2018/10/10 19:00
 */
class MainAdatper(data: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_txt, data) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper?.setText(R.id.mTv, item)
    }


    override fun getItem(position: Int): String? {
        val newPosition = position % data.size
        return getData().get(newPosition)
    }

    override fun getItemViewType(position: Int): Int {
        var count = getHeaderLayoutCount() + getData().size
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        if (count <= 0) {
            count = 1
        }
        var newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

}