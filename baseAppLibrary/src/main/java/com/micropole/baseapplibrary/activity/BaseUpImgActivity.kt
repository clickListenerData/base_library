package com.micropole.baseapplibrary.activity

import android.content.Intent
import android.os.Bundle
import com.micropole.baseapplibrary.util.ImageChooseHelper
import com.xx.baseuilibrary.mvp.BaseMvpActivity
import com.xx.baseuilibrary.mvp.BaseMvpView
import com.xx.baseuilibrary.mvp.presenter.BaseMvpPresenter

/**
 * @ClassName       BaseUpImgActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 16:23
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseUpImgActivity<P : BaseMvpPresenter<*,out BaseMvpView>> : BaseMvpActivity<P>() {

    lateinit var imageChooseHelper : ImageChooseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageChooseHelper = ImageChooseHelper(this){}
        imageChooseHelper.takePhoto.onCreate(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        imageChooseHelper.takePhoto.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imageChooseHelper.takePhoto.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imageChooseHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}