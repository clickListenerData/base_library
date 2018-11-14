package com.micropole.baseapplibrary.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * @ClassName       GuidePagerAdapter
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/13 9:21
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
class GuidePagerAdapter(val datas : List<View>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int = datas.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = datas[position]
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(datas[position])
    }
}