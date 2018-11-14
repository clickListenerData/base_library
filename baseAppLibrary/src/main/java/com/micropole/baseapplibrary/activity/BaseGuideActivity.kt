package com.micropole.baseapplibrary.activity

import android.graphics.Color
import android.support.annotation.ColorInt
import android.view.View
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.adapter.GuidePagerAdapter
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_guide.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator

/**
 * @ClassName       BaseGuideActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/12 17:31
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseGuideActivity : BaseMvpViewActivity() {

    lateinit var mViews : List<View>

    override fun getActivityLayoutId(): Int = R.layout.activity_guide

    override fun initData() {
        mViews = getViews()
        vp_guide.adapter = GuidePagerAdapter(mViews)
        initMagicIndicator()
    }

    override fun initEvent() {
    }

    private fun initMagicIndicator() {
        val circleNavigator = CircleNavigator(this)
        circleNavigator.circleCount = mViews.size
        circleNavigator.circleColor = getCircleColor()
        circleNavigator.circleClickListener = CircleNavigator.OnCircleClickListener { index -> vp_guide.currentItem = index }
        magic_guide.navigator = circleNavigator
        ViewPagerHelper.bind(magic_guide, vp_guide)
    }

    @ColorInt fun getCircleColor() : Int = Color.parseColor("#DDDDDD")

    abstract fun getViews() : List<View>
}