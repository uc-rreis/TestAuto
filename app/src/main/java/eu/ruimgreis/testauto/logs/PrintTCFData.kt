package eu.ruimgreis.testauto.logs

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.services.tcf.interfaces.TCFData
import com.usercentrics.sdk.services.tcf.interfaces.TCFPurpose
import com.usercentrics.sdk.services.tcf.interfaces.TCFVendor
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.ERROR_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.GAC_PROVIDERS_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.GAC_STRING_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.TCF_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.TCSTRING_TAG
import eu.ruimgreis.testauto.utils.getSettingsId

fun printTCFData() {

    val isACMV2Enabled = Usercentrics.instance.getCMPData().settings.tcf2?.acmV2Enabled ?: false

    println("Calling getTCFData")
    Usercentrics.instance.getTCFData { tcfData: TCFData ->
        Usercentrics.isReady({ _ ->
            val purposes = tcfData.purposes
            val specialPurposes = tcfData.specialPurposes
            val features = tcfData.features
            val specialFeatures = tcfData.specialFeatures
            val stacks = tcfData.stacks
            val vendors = tcfData.vendors
            val tcString = tcfData.tcString
            val purposesList = purposes.sortedBy { tcfPurpose -> tcfPurpose.id }
            var vendorsList = vendors.filter { tcfVendor -> tcfVendor.consent == true }
            var vendorsListFalse = vendors.filter { tcfVendor -> tcfVendor.consent == false }
            vendorsList = vendorsList.sortedBy { tcfVendor -> tcfVendor.id }
            var vendorsListLI = vendors.filter { tcfVendor -> tcfVendor.legitimateInterestConsent == true }
            vendorsListLI = vendorsListLI.sortedBy { tcfVendor -> tcfVendor.id }

            printPurposes(purposesList)
            printNumberVendors(vendors)

            printVendorsConsent(vendorsList, true)
            //printVendorsConsent(vendorsListFalse, false)

            //printVendorsLITrue(vendorsListLI)

            printTCString()

            if(isACMV2Enabled){
                printGACData()
            }

        },{ error ->
            Log.d(
                TCF_TAG, "Error: ${error.message}")
        })

    }
}

private fun printVendorsLITrue(vendorsListLI: List<TCFVendor>) {
    Log.d(TCF_TAG, "-- VENDORS WITH LI TRUE: ${vendorsListLI.size} --")
    for (vendor in vendorsListLI) {
        Log.d(
            TCF_TAG,
            "${vendor.name.padEnd(55, ' ')} | Id: ${vendor.id.toString().padEnd(4, ' ')}")
    }
}

private fun printVendorsConsent(vendorsList: List<TCFVendor>, consent: Boolean) {
    Log.d(TCF_TAG, "-- VENDORS WITH CONSENT ${consent.toString().uppercase()}: ${vendorsList.size} --")
    for (vendor in vendorsList) {
        Log.d(TCF_TAG, "${vendor.name.padEnd(55, ' ')} | Id: ${vendor.id.toString().padEnd(4, ' ')}")
    }
}

private fun printNumberVendors(vendors: List<TCFVendor>) {
    Log.d(TCF_TAG, "-- NUMBER OF VENDORS: ${vendors.size} --")
}

private fun printPurposes(purposesList: List<TCFPurpose>) {
    Log.d(TCF_TAG, "-- PURPOSES --")
    Log.d(TCF_TAG, "Purposes Size: ${purposesList.size}")
    for (purpose in purposesList)
        Log.d(
            TCF_TAG,
            "${purpose.name.padEnd(55, ' ')} | Id: ${
                purpose.id.toString().padEnd(2, ' ')
            } | Consent: ${purpose.consent} | Legitimate Interest: ${purpose.legitimateInterestConsent}".trimMargin()
        )
}

fun printTCString() {
    Usercentrics.instance.getTCFData { tcfData: TCFData ->
        // TCString"
        Log.d(TCF_TAG, "-- TCSTRING --")
        Log.d(TCSTRING_TAG, "TCString: ${tcfData.tcString}")
        Log.d(
            TCF_TAG,
            "SettingsId: ${getSettingsId()}. TCString Size: ${tcfData.tcString.length}"
        )
    }
}

private fun initTCFString() {
    Usercentrics.isReady({
        Usercentrics.instance.getTCFData { tcfData ->
            if (tcfData.tcString.toString().isEmpty()) {
                Log.d(TCF_TAG, "TC_STRING(On opening first layer): No TC String yet defined!")
            } else {
                Log.d(TCF_TAG, "TC_STRING(On opening first layer): ${tcfData.tcString}")
            }
        }
    }, {
        Log.d(ERROR_TAG, "Error: ${it.message}")
    })
}


fun printGACData() {
    val gacData = Usercentrics.instance.getAdditionalConsentModeData()
    val gacString = gacData.acString
    val gacProviders = gacData.adTechProviders

    Log.d(GAC_STRING_TAG, " -- AC String -- ")
    Log.d(GAC_STRING_TAG, gacString)

    Log.d(GAC_PROVIDERS_TAG, "-- Ad Tech Providers --")
    for(provider in gacProviders) {
        Log.d(
            GAC_PROVIDERS_TAG,
            "${provider.name.padEnd(55, ' ')} | Id: ${
                provider.id.toString().padEnd(2, ' ')
            } | Consent: ${provider.consent} | URL: ${provider.privacyPolicyUrl}".trimMargin()
        )
    }
}

fun printTCFChanges(appContext: Context) {
    PreferenceManager.getDefaultSharedPreferences(appContext).registerOnSharedPreferenceChangeListener { prefs, key ->
        try {
            val isTCF = key!!.startsWith("IABTCF_")
            if (isTCF) {
                val (valueString, typeString) = try {
                    val value = prefs.all[key]
                    val type = value!!::class.java.simpleName
                    value.toString() to type
                } catch (e: Throwable) {
                    "" to ""
                }
                Log.i("IABTCF", "key=$key type=$typeString value=$valueString")
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}