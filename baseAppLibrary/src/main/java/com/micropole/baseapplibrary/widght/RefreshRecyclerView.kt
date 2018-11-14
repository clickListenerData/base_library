package com.micropole.baseapplibrary.widght

import android.content.Context
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.baseapplibrary.R

/**
 * @ClassName       RefreshRecyclerView
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/9 14:18
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class RefreshRecyclerView@JvmOverloads constructor(context : Context, attrs : AttributeSet? = null) : SwipeRefreshLayout(context, attrs){

    var mRecyclerView : RecyclerView = RecyclerView(context)

    var mCurrentPage = 1

    var mAdapter : BaseQuickAdapter<out Any,out BaseViewHolder>? = null
    set(value) {
        if (mLayoutManager != null){
            mRecyclerView.layoutManager = mLayoutManager
            mRecyclerView.adapter = value
        }
        value?.bindToRecyclerView(mRecyclerView)
        value?.setEmptyView(R.layout.item_view_empty,this)
        field = value
    }

    var mLayoutManager : RecyclerView.LayoutManager? = null
    set(value) {
        if (mAdapter != null){
            mRecyclerView.layoutManager = value
            mRecyclerView.adapter = mAdapter
        }
        field = value
    }

    init {
        val params = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        addView(mRecyclerView,params)
    }

    fun setRefreshLoadMoreListener(action: (page:Int) -> Unit){
        setOnRefreshListener {
            if (!mAdapter!!.isLoading){
                mCurrentPage = 1
                action.invoke(mCurrentPage)
            }
        }
        mAdapter?.setOnLoadMoreListener({
            if (!isRefreshing){
                ++mCurrentPage
                action.invoke(mCurrentPage)
            }
        },mRecyclerView)
    }

    fun refreshError(){
        isRefreshing = false
        mAdapter?.loadMoreEnd(true)
        mAdapter?.setEnableLoadMore(false)
    }


}