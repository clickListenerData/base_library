package com.micropole.baseapplibrary.activity

import android.support.annotation.DrawableRes
import android.view.KeyEvent
import android.view.WindowManager
import com.blankj.utilcode.util.PermissionUtils
import com.micropole.baseapplibrary.R
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.view_splash.*

/**
 * @ClassName       BaseSplashActivity
 * @Description     权限申请 跳转
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/12 15:23
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseSplashActivity : BaseMvpViewActivity() {

    var isBack = true

    override fun getActivityLayoutId(): Int = R.layout.view_splash

    override fun initData() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        splash_view.setBackgroundResource(getBg())
    }

    override fun initEvent() {

    }

    fun permission(vararg mission : String){
        PermissionUtils.permission(*mission).callback(object : PermissionUtils.SimpleCallback {
            override fun onGranted() {
                isPermisson(true)
            }

            override fun onDenied() {
                isPermisson(false)
            }

        }).rationale { it.again(true) }.request()
    }

    @DrawableRes abstract fun getBg() : Int

    abstract fun isPermisson(isGranted : Boolean)

    /**
     * 拦截返回键
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && isBack){
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}