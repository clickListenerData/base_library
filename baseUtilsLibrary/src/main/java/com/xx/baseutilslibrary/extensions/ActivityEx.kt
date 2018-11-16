package com.xx.baseutilslibrary.extensions

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.blankj.utilcode.util.ToastUtils

/**
 * author: Gubr
 * date: 2018/5/8
 * describe:
 */
inline fun<reified T : Activity> Activity.startActivity(){
    val intent = Intent(this, T::class.java)
    this.startActivity(intent)
}

fun Activity.toast(content: String, duration: Int=Toast.LENGTH_SHORT){
    if (duration == Toast.LENGTH_SHORT){
        ToastUtils.showShort(content)
    }else{
        ToastUtils.showLong(content)
    }
}

