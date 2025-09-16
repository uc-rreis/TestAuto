package eu.ruimgreis.testauto.logs

import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsServiceConsent
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.CONSENT_TAG

fun printGDPRData(
    consents: List<UsercentricsServiceConsent>?
) {
    //val data = Usercentrics.instance.getCMPData()
    //val settings = data.settings
    //printDPSImplicitConsents(data)

    printDPSExplicitConsents()
}

private fun printDPSExplicitConsents() {
    val consents = Usercentrics.instance.getConsents()
//    val ucConsent = consents.first {it.templateId == "H1Vl5NidjWX"}
//    println("OPPO CONSENT VALUE: ${ucConsent.templateId} - ${ucConsent.status}")

    if (consents.isEmpty()) {
        Log.d(CONSENT_TAG,"No explicit consents given by user yet!");
    } else {
        Log.d(CONSENT_TAG,"-- EXPLICIT CONSENTS --");
        for (consent in consents) {
            Log.d(
                CONSENT_TAG,
                "${consent.dataProcessor.padEnd(50, ' ')} | " +
                        "Consent Status: ${consent.status.toString().padEnd(7, ' ')}" +
                        " | Type: ${consent.type.toString().padEnd(10, ' ')}"
            )

            Log.d(CONSENT_TAG,"History: ")
            for(it in consent.history) {
                Log.d(
                    CONSENT_TAG,"Status: ${it.status.toString().padEnd(7, ' ')} | " +
                        "Type: ${it.type.toString().padEnd(10, ' ')} | " +
                        "TimeStamp: ${it.timestampInMillis.toString().padEnd(15, ' ')} | ")
            }
        }
    }
}

//private fun printDPSImplicitConsents(data: UsercentricsCMPData) {
//    // Non-TCF Data - if you have services not included in IAB
//    val services = data.services
//    val categories = data.categories
//    Log.d(SDKDefaults.CONSENT_TAG,"-- IMPLICIT CONSENTS --")
//    Log.d(SDKDefaults.CONSENT_TAG,"Number of DPS: ${services.size}")
//    for (service in services) {
//        Log.d(
//            SDKDefaults.CONSENT_TAG,
//            "${service.dataProcessor.toString().padEnd(50, ' ')} | Default Consent Status: " +
//                    "${service.defaultConsentStatus.toString().padEnd(7, ' ')}"
//        )
//    }
//}