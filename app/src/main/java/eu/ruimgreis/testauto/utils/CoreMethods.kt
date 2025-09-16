package eu.ruimgreis.testauto.utils

import android.util.Log
import com.usercentrics.sdk.Usercentrics
import eu.ruimgreis.testauto.model.SDKDefaults
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.ERROR_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.LOG_TAG

fun setSettingsId (settingsId: String){
    SDKDefaults.settingsId = settingsId
}

fun getSettingsId(): String {
    var settingsId = "egLMgjg9j"
    if(SDKDefaults.settingsId.isNotEmpty()){
        settingsId = SDKDefaults.settingsId
    }

    return settingsId
}

fun clearUserSession() {
    Usercentrics.instance.clearUserSession({ status ->
        // This callback is equivalent to isReady API
        Log.d(LOG_TAG, "User Session Cleared")
        val userLocation = Usercentrics.instance.getCMPData().userLocation.countryCode
        val settingsId = Usercentrics.instance.getCMPData().settings.settingsId
        Log.d(LOG_TAG, "User Location: $userLocation")
        Log.d(LOG_TAG, "SettingsId: $settingsId")

    }, { error ->
        // Handle non-localized error
        Log.d(ERROR_TAG, "User Session Clearance failed: ${error.message}")
    })
}

fun catchLinks(){

}