package eu.ruimgreis.testauto.utils

import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsServiceConsent
import com.usercentrics.sdk.models.common.UsercentricsVariant
import eu.ruimgreis.testauto.logs.printCCPAData
import eu.ruimgreis.testauto.logs.printGDPRData
import eu.ruimgreis.testauto.logs.printTCFData


/**
 * Apply user given consents
 * In this simple form, it simply logs default gdpr consents to console
 */
fun applyConsents(consents: List<UsercentricsServiceConsent>?) {
    getCMPData(consents)
}

/**
 * Logs settings and user given consents, GDPR, TCF, CCPA
 * Comment/Uncomment whatever you want to log or not
 */

fun getCMPData(consents: List<UsercentricsServiceConsent>?) {
    Usercentrics.isReady({
        // CMP Data
        printGDPRData(consents)

        if(Usercentrics.instance.getCMPData().activeVariant == UsercentricsVariant.TCF) {
            printTCFData()
        }

        if(Usercentrics.instance.getCMPData().activeVariant == UsercentricsVariant.CCPA) {
            printCCPAData()
        }

    }, {
        print("Error: ${it.message}")
    })
}