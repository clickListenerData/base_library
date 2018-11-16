package com.micropole.baseapplibrary.appconst;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.micropole.baseapplibrary.widght.RefreshRecyclerView;

import java.util.List;

/**
 * @ClassName BindAdapter
 * @Description support kotlin ???
 * @Author HuaiXianZhong
 * @Sign 。。。
 * @Date 2018/11/13 14:56
 * @Copyright Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
public class BindAdapter {

    @BindingAdapter(value = "android:image_url", requireAll = false)
    public static void setImageUrl(ImageView view,  String url) {
        com.micropole.baseapplibrary.appconst.AdapterKt.setUrl(view,url);
    }

    @BindingAdapter(value = "android:text_html",requireAll = false)
    public static void setTextHtml(TextView view,String html){
        com.xx.baseutilslibrary.extensions.ViewExKt.setHtmlText(view,html);
    }

    @BindingAdapter(value = {"android:convenient_banner_data","android:convenient_banner_is_turn"},requireAll = false)
    public static void setBannerData(ConvenientBanner view, List<String> data,Long isTurn){
        com.micropole.baseapplibrary.appconst.AdapterKt.setTurnImage(view,data,isTurn);
    }

    @BindingAdapter(value = {"android:rv_layout_manager","android:rv_adapter"},requireAll = true)
    public static void setRvLA(RecyclerView view,RecyclerView.LayoutManager mLayoutManager,RecyclerView.Adapter mAdapter){
        view.setLayoutManager(mLayoutManager);
        view.setAdapter(mAdapter);
    }

}
