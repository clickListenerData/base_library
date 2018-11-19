package com.micropole.newproject

import com.micropole.baseapplibrary.activity.BaseUpImgActivity
import com.xx.baseuilibrary.mvp.BaseMvpView
import com.xx.baseuilibrary.mvp.presenter.BaseMvpPresenter
import kotlinx.android.synthetic.main.activity_second.*
import org.devio.takephoto.model.TResult

/**
 * @ClassName       UpImgActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/19 9:28
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class UpImgActivity : BaseUpImgActivity<UpImgActivity.Contract.Present>() {
    override fun createPresenter(): Contract.Present {
        return Contract.Present()
    }

    override fun getActivityLayoutId(): Int {
        return R.layout.activity_second
    }

    override fun initData() {
        rv.imageChooseHelper = imageChooseHelper
    }

    override fun initEvent() {
        tv.setOnClickListener {
            showToast(rv.mImgList.size.toString())
        }
    }

    class Contract(){
        class Model()

        class Present : BaseMvpPresenter<Model,BaseMvpView>(){
            override fun createModel(): Model {
                return Model()
            }
        }
    }

    override fun imgResult(result: TResult) {
        runOnUiThread { rv.addImgView(result.images[0].compressPath) }
    }
}