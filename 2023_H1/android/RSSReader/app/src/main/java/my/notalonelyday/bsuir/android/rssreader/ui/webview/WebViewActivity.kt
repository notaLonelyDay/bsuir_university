package my.notalonelyday.bsuir.android.rssreader.ui.webview

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import my.notalonelyday.bsuir.android.rssreader.R


class WebViewActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val webView = findViewById<View>(R.id.webview) as WebView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.loadUrl(
            intent.extras?.getString(KEY_URL) ?: "android.developer.com"
        )
    }

    companion object {
        private const val KEY_URL = "key_url"
        fun create(fromActivity: Context, url: String?): Intent {
            return Intent(fromActivity, WebViewActivity::class.java)
                .putExtra(KEY_URL, url)
        }
    }
}