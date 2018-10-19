package com.kermitye.autoscroll

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * Created by kermitye on 2018/10/19 17:20
 */
abstract class AutoScrollAdapter<T, K : BaseViewHolder>(layoutResId: Int = 0, data: List<T>? = null) : BaseQuickAdapter<T, K>(layoutResId, data) {
    //TODO 暂未完善，请查看主程序MainActivity
}