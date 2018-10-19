package com.kermitye.autoscrooldemo

import android.graphics.PointF
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSmoothScroller
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var mAutoTask: Disposable? = null
    lateinit var mSmoothScroll: LinearSmoothScroller

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data = arrayListOf<String>()
        for (i in 1..15) {
            data.add("测试数据$i")
        }

        mRv.layoutManager = LinearLayoutManager(this)
        mRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mRv.adapter = MainAdatper(data)

        //自定义LinearSmoothScroller，重写方法，滚动item至顶部，控制滚动速度
        mSmoothScroll = object : LinearSmoothScroller(this) {
            override fun getVerticalSnapPreference(): Int {
                return LinearSmoothScroller.SNAP_TO_START
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
                // 移动一英寸需要花费3ms
                return 3f / (displayMetrics?.density ?: 1f)
            }

        }

        mRvTwo.layoutManager = LinearLayoutManager(this)
        mRvTwo.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mRvTwo.adapter = MainAdatper(data)

    }

    override fun onStart() {
        super.onStart()
        startAuto()
        mRvTwo.start()
    }

    override fun onStop() {
        super.onStop()
        stopAuto()
        mRvTwo.stop()
    }

    fun startAuto() {
        if (mAutoTask != null && (mAutoTask?.isDisposed ?: true))
            mAutoTask?.dispose()

        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe {
            //定位到指定项如果该项可以置顶就将其置顶显示
            mSmoothScroll.targetPosition = it.toInt()
            (mRv.layoutManager as LinearLayoutManager).startSmoothScroll(mSmoothScroll)
        }
    }

    fun stopAuto() {
        if (mAutoTask != null && (mAutoTask?.isDisposed ?: true)) {
            mAutoTask?.dispose()
            mAutoTask = null
        }
    }
}
