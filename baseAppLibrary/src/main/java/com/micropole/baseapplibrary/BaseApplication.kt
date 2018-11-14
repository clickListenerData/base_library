package com.micropole.baseapplibrary

import android.support.multidex.MultiDexApplication
import com.blankj.utilcode.util.Utils

/**
 * @ClassName       BaseApplication
 * @Description     application
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/9 13:48
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
open class BaseApplication : MultiDexApplication(){

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
    }
}