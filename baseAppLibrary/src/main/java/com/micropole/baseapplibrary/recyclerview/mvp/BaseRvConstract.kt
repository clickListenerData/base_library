package com.micropole.baseapplibrary.recyclerview.mvp

import com.xx.baseuilibrary.mvp.BaseMvpView
import com.xx.baseuilibrary.mvp.presenter.BaseMvpPresenter

/**
 * @ClassName       BaseRvConstract
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 14:52
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
interface BaseRvConstract {

    interface View : BaseMvpView{
        fun refreshError()
    }

    interface Model{
    }

    abstract class Present : BaseMvpPresenter<Model,View>(){}
}