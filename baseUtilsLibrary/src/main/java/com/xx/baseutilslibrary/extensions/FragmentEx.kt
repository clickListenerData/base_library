package com.xx.baseutilslibrary.extensions

import android.app.Activity
import android.support.v4.app.Fragment

import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils

/**
 * author: Gubr
 * date: 2018/5/9
 * describe:
 */
fun Fragment.toast(content: String, duration: Int= Toast.LENGTH_SHORT){
    if (duration == Toast.LENGTH_SHORT){
        ToastUtils.showShort(content)
    }else{
        ToastUtils.showLong(content)
    }
}