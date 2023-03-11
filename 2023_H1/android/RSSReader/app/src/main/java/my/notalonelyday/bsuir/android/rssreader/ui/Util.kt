package my.notalonelyday.bsuir.android.rssreader.ui

import android.content.Context
import my.notalonelyday.bsuir.android.rssreader.ui.webview.WebViewActivity

fun handleUri(mContext: Context, uri: String?) {
    mContext.startActivity(
        WebViewActivity.create(
            mContext,
            uri
        )
    )
}
