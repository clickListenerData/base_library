package com.micropole.baseapplibrary.activity

import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.RegexUtils
import com.xx.baseuilibrary.mvp.BaseMvpViewActivity
import com.xx.baseutilslibrary.common.XxResourceUtil

/**
 * @ClassName       BaseWebActivity
 * @Description     todo
 * @Author          HuaiXianZhong
 * @Sign            。。。
 * @Date            2018/11/12 17:46
 * @Copyright       Guangzhou micro pole mobile Internet Technology Co., Ltd.
 */
abstract class BaseWebActivity : BaseMvpViewActivity() {

    override fun initData() {
        val id = XxResourceUtil.getId(this, "")
        val webview = findViewById<WebView>(id) ?: throw RuntimeException("web view must be not null")
        webview.settings.setSupportZoom(false)
        webview.isHorizontalScrollBarEnabled = false
        webview.isVerticalScrollBarEnabled = false
        webview.isScrollContainer = false
        with(webview.settings) {
            javaScriptEnabled = true
            blockNetworkImage = false
            useWideViewPort = false
            layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.SINGLE_COLUMN
            layoutAlgorithm = android.webkit.WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            builtInZoomControls = false // 设置显示缩放按钮
            setSupportZoom(false) // 支持缩放
            if (android.os.Build.VERSION.SDK_INT >= 21)
            //Build.VERSION_CODES.LOLLIPOP
                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
            }
        }

        val data = getData()
        if (!data.isEmpty()){
            if (RegexUtils.isURL(data)){
                webview.loadUrl(data)
            }else{
                webview.loadDataWithBaseURL("",data,"text/html","utf-8","")
            }
        }else{
            showToast("data must be not null")
        }
    }

    override fun initEvent() {

    }

    abstract fun getData() : String
}