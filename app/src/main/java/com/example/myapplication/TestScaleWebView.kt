package com.example.myapplication

import android.os.Build
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.fragment.app.FragmentActivity

class TestScaleWebView : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)
        setContentView(webView)

        val wSettings = webView.settings
        wSettings.javaScriptEnabled = true
        wSettings.javaScriptCanOpenWindowsAutomatically = true
        wSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        wSettings.allowFileAccess = true
        wSettings.cacheMode = WebSettings.LOAD_DEFAULT
        wSettings.databaseEnabled = true
        wSettings.domStorageEnabled = true
        wSettings.setGeolocationEnabled(true)
        wSettings.allowFileAccess = true
        wSettings.loadWithOverviewMode = true
        if (Build.VERSION.SDK_INT >= 21) {
            /**
             * 关于webview的http和https的混合请求的，
             * 从Android5.0开始，WebView默认不支持同时加载Https和Http混合模式。
             * 在API>=21的版本上面默认是关闭的，在21以下就是默认开启的，直接导致了在高版本上面http请求不能正确跳转。
             * 在WebView设置处配置Webview加载内容的混合模式
             */
            wSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        webView.setInitialScale(0)
        webView.isVerticalScrollBarEnabled = true
        webView.loadUrl("file:///android_asset/my.html")

        webView.settings.setSupportZoom(true)
        // 设置出现缩放工具
        webView.settings.builtInZoomControls = true
        // 为图片添加放大缩小功能
        webView.settings.useWideViewPort = true
        webView.settings.displayZoomControls = false
        webView.setInitialScale(0)
    }
}