package com.micropole.baseapplibrary.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

/**
 * @ClassName       DataBindAdapter
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/8 10:07
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class DataBindAdapter<T>(val brId : Int, @LayoutRes val id:Int, var br:Int = -1) : BaseQuickAdapter<T,DataBindAdapter.DataBindViewHolder>(id) {
    override fun convert(helper: DataBindViewHolder?, item: T) {
        helper?.binding?.setVariable(brId,item)
        if (br >= 0) helper?.binding?.setVariable(br,{view : View -> onItemChildClickListener?.onItemChildClick(this,view,helper.adapterPosition)})
        helper?.binding?.executePendingBindings()
    }

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): DataBindViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(mContext), id, parent, false)
        return DataBindViewHolder(binding)
    }

    class DataBindViewHolder(val binding : ViewDataBinding) : BaseViewHolder(binding.root)

}