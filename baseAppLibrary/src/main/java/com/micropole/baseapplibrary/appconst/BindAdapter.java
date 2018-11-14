package com.micropole.baseapplibrary.appconst;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * @ClassName BindAdapter
 * @Description todo
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
}
