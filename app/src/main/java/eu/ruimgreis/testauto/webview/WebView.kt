package eu.ruimgreis.testauto.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import eu.ruimgreis.testauto.utils.getSettingsId

fun getURL(): String {
    return "https://app.usercentrics.eu/browser-ui/preview/index.html?settingsId=${getSettingsId()}"
    //return "https://web.cmp.usercentrics-sandbox.eu/ui/pr/602/index.html?settingsId=IjaOij_6w&cmpVersion=3&settingsType=gdpr"
    //return "https://web.cmp.usercentrics-sandbox.eu/ui/pr/602/index.html?settingsId=A5kxyiGIlRnPdd"
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AddWebView(){
    AndroidView(
        modifier = Modifier.fillMaxHeight(),
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true

                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
                settings.allowContentAccess = true
                addJavascriptInterface(JavascriptInterface(), "ucMobileSdk")
            }
        },
        update = { webView ->
            webView.loadUrl(getURL())
        }
    )
}