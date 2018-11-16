package com.micropole.baseapplibrary.appconst

import android.view.View
import android.widget.ImageView
import com.bigkoo.convenientbanner.ConvenientBanner
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator
import com.bigkoo.convenientbanner.holder.Holder
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.micropole.baseapplibrary.R
import com.micropole.baseapplibrary.util.ImageHolderView
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
    if (this.isLoading){
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

fun ConvenientBanner<*>.setTurnImage(mImgs:List<String>,isturn : Long = 2000) : ConvenientBanner<*>{
    this as ConvenientBanner<String>
    setPages({ImageHolderView()} , mImgs)
    setPageIndicator(intArrayOf(R.drawable.shape_indicator_gray, R.drawable.shape_indicator_red))
    setPointViewVisible(true)
    if (isturn != 0L) startTurning(isturn)

    return this
}