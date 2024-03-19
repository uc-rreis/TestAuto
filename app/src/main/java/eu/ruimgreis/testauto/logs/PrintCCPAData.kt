package eu.ruimgreis.testauto.logs

import android.util.Log
import com.usercentrics.sdk.Usercentrics
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.CCPA_TAG

fun printCCPAData(){
    Usercentrics.isReady({ _ ->
        val ccpaData = Usercentrics.instance.getUSPData()
        val uspString = ccpaData.uspString
        Log.d(CCPA_TAG, "CCPAString: $uspString")
    },{ error ->
        Log.d(CCPA_TAG, "Error: ${error.message}")
    })
}

//fun isAudioAdAllowed() {
//    Usercentrics.instance.getTCFData { data ->
//        val consents = Usercentrics.instance.getConsents()
//        val serviceConsents = consents?.associate { consent ->
//            Pair(
//                first = consent.templateId,
//                second = consent.status,
//            )
//        } ?: emptyMap()
//        val vendorConsents = data.vendors.associate { vendor ->
//            Pair(
//                first = vendor.id.toString(),
//                second = vendor.consent == true || vendor.legitimateInterestConsent == true,
//            )
//        }
//        vendorMap = serviceConsents + vendorConsents
//    }
//}
//fun hasVendorConsent(id: String) = vendorMap[id]
