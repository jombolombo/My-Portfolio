package com.example.newsapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

/**
 * class the displays full article
 */
class ReadNewsActivity : AppCompatActivity() {

    /**
     * creates webview and populates it with a url
     */
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_news)
        val webView = findViewById<WebView>(R.id.myWebview)
        val EXTRA_MESSAGE = "url"
        val url = intent.getStringExtra(EXTRA_MESSAGE)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl(url)
    }
}