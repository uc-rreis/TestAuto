package eu.ruimgreis.testauto.layer

import android.content.Context
import android.util.Log
import com.usercentrics.sdk.PopupPosition
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsLayout
import eu.ruimgreis.testauto.MainActivity
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.ERROR_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.LOG_TAG

fun showCMP(ucLayout: UsercentricsLayout?, appContext: Context) {
    //checkConnection(appContext)
    showFirstLayer(appContext, ucLayout!!)
}

fun getVersionName(activity: MainActivity): String {
    val pInfo = activity.packageManager.getPackageInfo(activity.packageName, 0)
    return pInfo.versionName ?: "test"
}

/**
Banner API V2
defines settings for this setup
 */


fun showFirstLayer(context: Context, popup: UsercentricsLayout?) {

    Log.d(LOG_TAG, "Showing first layer")
    //initTCFString()

    Usercentrics.isReady({
        // Create a UsercentricsUserInterface instance
        print("Should collect consent: ${it.shouldCollectConsent}")
        val ui = UsercentricsBanner(context, getBannerSettings(context, popup))
        ui.showFirstLayer() { userResponse ->
//            Usercentrics.instance.getTCFData { tcData ->
//                Log.d(SDKDefaults.TCF_TAG, "TCF at INIT: ${tcData.tcString}")
//            }
//            printUserInteraction(userResponse)
//            printTCFChanges(context)
//            // Apply Consent
//            applyConsents(userResponse?.consents)
        }
    }, {
        print("Error: ${it.message}")
    })
}

fun showSecondLayer(appContext:Context){

    Log.d("[USERCENTRICS][DEBUG]", "Showing first layer")

    Usercentrics.isReady({
        val ui = UsercentricsBanner(
            appContext,
            getBannerSettings(appContext, UsercentricsLayout.Popup(PopupPosition.CENTER))
        )
        ui.showSecondLayer { userResponse ->
            // Apply Consent
            Log.d(LOG_TAG, "Second Layer Shown")
//            printTCFChanges(appContext)
//            applyConsents(userResponse?.consents)
        }
    }, {
        Log.d(ERROR_TAG, "Error: ${it.message}")
    })
}