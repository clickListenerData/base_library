package com.micropole.baseapplibrary.activity

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.view.KeyEvent
import com.blankj.utilcode.util.ActivityUtils
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.appconst.Constants
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import net.lucode.hackware.magicindicator.FragmentContainerHelper
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView

/**
 * @ClassName       BaseNavigationActivity
 * @Description     底部导航栏 fragment activity
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/10/16 16:32
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseNavigationActivity : BaseMvpViewActivity() {

    val mFragmentContainerHelper = FragmentContainerHelper()

    var mFragments : List<Fragment>? = null

    override fun getActivityLayoutId(): Int = R.layout.activity_navigation

    override fun initData() {
        initNavigation()
        if (mFragments == null){
            mFragments = getFragments()
        }
        checkItem(0)
    }

    fun checkItem(index: Int) {
        mFragmentContainerHelper.handlePageSelected(index, false)
        swithPagers(index)
    }

    override fun initEvent() {
    }

    fun initNavigation(){
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(p0: Context?, p1: Int): IPagerTitleView? {
                return getPagerTitleView(index = p1)
            }

            override fun getCount(): Int {
                return getFragments().size
            }

            override fun getIndicator(p0: Context?): IPagerIndicator? {
                return null
            }
        }
        val mNavigation = findViewById<MagicIndicator>(R.id.navigation_magic)
        mNavigation.navigator = commonNavigator
        mFragmentContainerHelper.attachMagicIndicator(mNavigation)
    }

    abstract fun getPagerTitleView(index : Int) : IPagerTitleView

    abstract fun getFragments() : List<Fragment>

    fun swithPagers(index : Int){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment
        var i = 0
        val j = mFragments!!.size
        while (i < j) {
            if (i == index) {
                i++
                continue
            }
            fragment = mFragments!!.get(i)
            if (fragment.isAdded) {
                fragmentTransaction.hide(fragment)
            }
            i++
        }
        fragment = mFragments!!.get(index)
        if (fragment.isAdded) {
            fragmentTransaction.show(fragment)
        } else {
            fragmentTransaction.add(R.id.fl_content, fragment)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent) //重新启动 main activity
        val index = intent?.getIntExtra(Constants.MAIN_INDEX_ARG, 0)
        if (index != null && index < mFragments!!.size) checkItem(index)
    }

    /**
     * 双击退出APP
     */
    var mBackTime = 0L
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - mBackTime < 1500){
                ActivityUtils.finishAllActivities(true)
            }else{
                mBackTime = System.currentTimeMillis()
                showToast("再按一次回到桌面")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}