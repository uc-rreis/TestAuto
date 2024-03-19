package eu.ruimgreis.testauto.webview

import android.util.Log
import android.webkit.JavascriptInterface
import com.usercentrics.sdk.Usercentrics
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.JS_LOG_TAG

class JavascriptInterface() {

    @JavascriptInterface
    fun getUserSessionData(): String {
        return Usercentrics.instance.getUserSessionData().also {
            Log.d(JS_LOG_TAG, "UserSessionData: $it")
        }
    }
}