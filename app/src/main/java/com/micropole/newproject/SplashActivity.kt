package com.micropole.newproject

import android.Manifest
import android.annotation.SuppressLint
import com.blankj.utilcode.util.CrashUtils
import com.micropole.baseapplibrary.activity.BaseSplashActivity

/**
 * @ClassName       SplashActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/12 16:42
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class SplashActivity : BaseSplashActivity() {

    override fun initData() {
        super.initData()
        permission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun getBg(): Int = R.drawable.bg_hotel

    @SuppressLint("MissingPermission")
    override fun isPermisson(isGranted: Boolean) {
        if (isGranted){
            CrashUtils.init()
            startActivityThenFinishSelf(MainActivity::class.java)
        }
    }
}