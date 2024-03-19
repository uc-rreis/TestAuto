package eu.ruimgreis.testauto.logs

import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.CMP_DATA_TAG

fun printCMPData() {
    var data = Usercentrics.instance.getCMPData()
    Log.d(CMP_DATA_TAG, "PUBLISHED APPS: ${data.settings.publishedApps}")
    var location = data.userLocation
}

fun printUserInteraction(userResponse: UsercentricsConsentUserResponse?) {
    Log.d(CMP_DATA_TAG, "User Interaction: ${userResponse?.userInteraction}")
}