package eu.ruimgreis.testauto.webview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.webkit.WebView
import eu.ruimgreis.testauto.utils.getSettingsId

fun showWebview(activity: Activity?, appContext: Context){
    val myWebView = WebView(appContext)
    // customer can manipulate webview
    activity?.setContentView(myWebView)
    restoreSessionInWebview(getURL(), myWebView)
}

@SuppressLint("SetJavaScriptEnabled")
private fun restoreSessionInWebview(url: String, myWebView: WebView) {
    // Making webview debuggable
    WebView.setWebContentsDebuggingEnabled(true);

    myWebView.settings.javaScriptEnabled = true
    myWebView.settings.domStorageEnabled = true
    myWebView.addJavascriptInterface(JavascriptInterface(), "ucMobileSdk")
    myWebView.loadUrl(getURL())
}

fun getURL(): String {
    return "https://app.usercentrics.eu/browser-ui/preview/index.html?settingsId=${getSettingsId()}"
}