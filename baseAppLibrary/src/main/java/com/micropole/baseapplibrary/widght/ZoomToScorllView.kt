package com.micropole.baseapplibrary.widght

import android.animation.ValueAnimator
import android.content.Context
import android.support.v4.widget.NestedScrollView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * @ClassName       ZoomToScorllView
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/14 14:17
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class ZoomToScorllView@JvmOverloads constructor(context : Context, attrs : AttributeSet? = null,def : Int = 0) : NestedScrollView(context, attrs,def) {

    //放大的view
    var mZoomView : View? = null

    //zoom view width and height
    private var mZoomViewWidht = 0
    private var mZoomViewHeight = 0

    //记录第一次按下的位置
    private var mFirstPosition = 0f

    //是否正在缩放
    private var mIsScorlling = false

    //缩放系数，系数越大，变化的越大
    var mScrollRate = 0.3f
    //回弹系数，越大，回弹越慢
    var mReplyRate = 0.5f

    override fun onFinishInflate() {
        super.onFinishInflate()
        overScrollMode = View.OVER_SCROLL_NEVER
        if (getChildAt(0) != null && getChildAt(0) is ViewGroup && mZoomView == null){
            val vg = (getChildAt(0) as ViewGroup)
            if(vg.childCount > 0){
                mZoomView = vg.getChildAt(0)
            }
        }
    }

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (mZoomViewWidht <= 0 || mZoomViewHeight <= 0){
            mZoomViewWidht = mZoomView!!.measuredWidth
            mZoomViewHeight = mZoomView!!.measuredHeight
        }

        when(ev?.action){
            MotionEvent.ACTION_MOVE -> {
                if (!mIsScorlling){
                    if (scrollY == 0){
                        mFirstPosition = ev.y
                    }else{
                        return true
                    }
                }

                val distance = (ev.y - mFirstPosition) * mScrollRate

                if (distance < 0){
                    return true
                }

                mIsScorlling = true
                setZoom(distance)
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (mIsScorlling){
                    mIsScorlling = false
                    replyView()
                }else{
                    performClick()
                }
            }
        }
        return super.onTouchEvent(ev)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    fun setZoom(distance : Float){
        if (mZoomViewWidht <= 0 || mZoomViewHeight <= 0){
            return
        }
        val lp = mZoomView?.layoutParams
        lp?.width = (mZoomViewWidht + distance).toInt()
        lp?.height = (mZoomViewHeight * ((mZoomViewWidht + distance) / mZoomViewWidht)).toInt()
        (lp as MarginLayoutParams).setMargins(-(lp.width - mZoomViewWidht) / 2 , 0,0,0)
        mZoomView?.layoutParams = lp
    }

    fun replyView(){
        val distance = (mZoomView!!.measuredWidth - mZoomViewWidht).toFloat()
        val va = ValueAnimator.ofFloat(distance, 0f).setDuration((distance * mReplyRate).toLong())
        va.addUpdateListener {
            setZoom(it.animatedValue as Float)
        }
        va.start()
    }
}