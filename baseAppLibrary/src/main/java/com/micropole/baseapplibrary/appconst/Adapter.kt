package com.micropole.baseapplibrary.appconst

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.xx.baseutilslibrary.extensions.loadImag

/**
 * @ClassName       Adapter
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/12 9:50
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */


fun<DATA : Any> BaseQuickAdapter<DATA,out BaseViewHolder>.setListData(data : List<DATA>){
    if (this?.isLoading!!){
        if (data.isNotEmpty()){
            this.loadMoreComplete()
            this.addData(data)
        }else{
            this.loadMoreEnd(false)
        }
    }else{
        this.setEnableLoadMore(true)
        this.setNewData(data)
        this.disableLoadMoreIfNotFullPage()
    }
}

fun setUrl(view:ImageView?,url:String?){
    view?.loadImag(url)
}