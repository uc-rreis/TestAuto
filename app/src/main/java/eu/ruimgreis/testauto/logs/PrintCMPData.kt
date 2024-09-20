package eu.ruimgreis.testauto.logs

import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsConsentUserResponse
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.CMP_DATA_TAG

fun printCMPData() {
    val data = Usercentrics.instance.getCMPData()
//    val services = data.services
//    for(service in services) {
//        Log.d(CMP_DATA_TAG, "SERVICE:  ${service.dataProcessor} -- LEGAL BASIS: ${service.legalBasisList}")
//    }

    data.services.forEach { service ->
        service.legalBasisList.forEach { legalBasis ->
            Log.d("UserCentricsUseCase", "${service.dataProcessor} - Legal Basis: $legalBasis - ${data.settings.labels.legalBasisInfo}")
        }
    }
    //Log.d(CMP_DATA_TAG, "PUBLISHED APPS: ${data.settings.publishedApps}")
    //var location = data.userLocation
}

fun printUserInteraction(userResponse: UsercentricsConsentUserResponse?) {
    Log.d(CMP_DATA_TAG, "User Interaction: ${userResponse?.userInteraction}")
}