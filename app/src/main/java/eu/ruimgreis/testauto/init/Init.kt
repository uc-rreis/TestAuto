package eu.ruimgreis.testauto.init

import android.content.Context
import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsBanner
import com.usercentrics.sdk.UsercentricsOptions
import com.usercentrics.sdk.models.common.UsercentricsLoggerLevel
import eu.ruimgreis.testauto.model.SDKDefaults
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.ERROR_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.LOG_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.SHOULD_COLLECT_CONSENT_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.tcfSettingsId

fun initCMP(appContext: Context, options: UsercentricsOptions) {
    val userOptions = getUserOptions(options.settingsId, options.ruleSetId)

    //checkConnection(appContext)

    Usercentrics.initialize(appContext, userOptions)
    Usercentrics.isReady({ status ->
        Log.w(SHOULD_COLLECT_CONSENT_TAG, "Should collect consent: ${status.shouldCollectConsent}")

        val userLocation = Usercentrics.instance.getCMPData().userLocation.countryCode
        Log.d(LOG_TAG, "User Location: $userLocation")

//        if(!SDKDefaults.controllerID.isNullOrEmpty()){
//            restoreSession()
//        }
//        printTCFChanges(appContext)
        val banner = UsercentricsBanner(context = appContext)
        banner.showFirstLayer {

        }
    },
        {
            Log.d(ERROR_TAG, "Error: ${it.message}")
        })
}

private fun getUserOptions(settingsId: String, rulesetId: String): UsercentricsOptions {
    var userOptions = UsercentricsOptions()

    if(rulesetId.isNotEmpty())
    {
        userOptions = UsercentricsOptions(
            ruleSetId = rulesetId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG,
            consentMediation = false
        )
        //userOptions.networkMode = NetworkMode.EU
        Log.d(LOG_TAG, "RuletSetId: $rulesetId")

    } else if(!settingsId.isNullOrEmpty()) {
        userOptions = UsercentricsOptions(
            settingsId = settingsId,
            defaultLanguage = "en",
            version = "latest",
            loggerLevel = UsercentricsLoggerLevel.DEBUG,
            consentMediation = false,
        )
        Log.d(LOG_TAG, "SettingsId: $settingsId")

    }

    return userOptions
}