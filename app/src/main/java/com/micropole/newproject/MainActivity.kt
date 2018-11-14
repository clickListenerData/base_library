package com.micropole.newproject

import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import com.micropole.baseapplibrary.adapter.DataBindAdapter
import com.micropole.baseapplibrary.appconst.setListData
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpViewActivity() {
    override fun getActivityLayoutId(): Int = R.layout.activity_main

    override fun initData() {
        rv.mLayoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
        val dataBindAdapter = DataBindAdapter<MainBean>(1, R.layout.item_rrv)
        rv.mAdapter = dataBindAdapter

        val arrayListOf = arrayListOf<MainBean>()
        for (i in 0..10){
            val mainBean = MainBean()
            mainBean.name = "1$i"
            arrayListOf.add(mainBean)
        }
        dataBindAdapter.setListData(arrayListOf)
    }

    override fun initEvent() {
        rv.setRefreshLoadMoreListener {
            Handler().postDelayed({
                val arrayListOf = arrayListOf<MainBean>()
                for (i in 11..20){
                    val mainBean = MainBean()
                    mainBean.name = "1$i"
                    arrayListOf.add(mainBean)
                }
                (rv.mAdapter as DataBindAdapter<MainBean>).setListData(arrayListOf)
                rv.isRefreshing = false
            },1000)
        }
    }
    /*override fun getPagerTitleView(index: Int): IPagerTitleView {

    }

    override fun getFragments(): List<Fragment> {
    }*/

}
