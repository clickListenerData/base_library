package com.micropole.baseapplibrary.recyclerview

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.recyclerview.mvp.BaseRvConstract
import com.xx.baseuilibrary.mvp.BaseMvpActivity
import com.xx.baseutilslibrary.common.XxResourceUtil

/**
 * @ClassName       BaseRefreshActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/16 14:48
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseRefreshActivity<T,P : BaseRvConstract.Present> : BaseMvpActivity<P>(), SwipeRefreshLayout.OnRefreshListener {

    var mCurrentPage = 1
    var refresh : SwipeRefreshLayout? = null
    var recyclerView : RecyclerView? = null
    var adapter : BaseQuickAdapter<T,out BaseViewHolder>? = null



    override fun initData() {
        val id = XxResourceUtil.getId(this, "swipe_refresh")
        val id1 = XxResourceUtil.getId(this, "recycler_view")
        refresh = findViewById(id)
        recyclerView = findViewById(id1)
        loadData(mCurrentPage)
    }

    fun setRvLa(layoutManager: RecyclerView.LayoutManager,adapter: BaseQuickAdapter<T,out BaseViewHolder>){
        recyclerView?.layoutManager = layoutManager
        adapter.setEmptyView(R.layout.item_view_empty,recyclerView)
        this.adapter = adapter
        recyclerView?.adapter = adapter
    }

    override fun initEvent() {
        refresh?.setOnRefreshListener(this)
        adapter?.setOnLoadMoreListener({
            loadData(++mCurrentPage)
        },recyclerView)
    }

    override fun onRefresh() {
        mCurrentPage = 1
        loadData(mCurrentPage)
    }

    fun refreshError(){
        refresh?.isRefreshing = false
        adapter?.loadMoreEnd()
        adapter?.setEnableLoadMore(false)
    }

    fun setData(data : List<T>){
        if (adapter != null){
            if (adapter!!.isLoading){
                if (data.isNotEmpty()){
                    adapter!!.loadMoreComplete()
                    adapter!!.addData(data)
                }else{
                    adapter!!.loadMoreEnd(false)
                }
            }else{
                refresh?.isRefreshing = false
                adapter!!.setNewData(data)
                adapter!!.disableLoadMoreIfNotFullPage()
            }
        }
    }

    abstract fun loadData(page : Int)
}