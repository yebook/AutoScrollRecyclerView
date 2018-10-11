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
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    var mAutoTask: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var data = arrayListOf<String>()
        for (i in 1..15) {
            data.add("测试数据$i")
        }

        var layoutManager = object : LinearLayoutManager(this) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
                var linearSmoothScroller = object : LinearSmoothScroller(recyclerView?.context) {

                    //返回滑动一个pixel需要多少毫秒
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics?): Float {
//                        return super.calculateSpeedPerPixel(displayMetrics)
                        return 3f / (displayMetrics?.density ?: 1f)
                    }
                }
                linearSmoothScroller.setTargetPosition(position)
                startSmoothScroll(linearSmoothScroller)
            }
        }

        mRv.layoutManager = layoutManager
        mRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mRv.adapter = MainAdatper(data)

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

        mAutoTask = Observable.interval(1, 2, TimeUnit.SECONDS).subscribe {
            mRv.smoothScrollToPosition((4 + it).toInt())
        }
    }

    fun stopAuto() {
        if (mAutoTask != null && (mAutoTask?.isDisposed ?: true)) {
            mAutoTask?.dispose()
            mAutoTask = null
        }
    }
}
