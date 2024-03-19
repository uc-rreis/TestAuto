package eu.ruimgreis.testauto.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.usercentrics.sdk.*
import com.usercentrics.sdk.models.common.UsercentricsVariant
import eu.ruimgreis.testauto.logs.printGDPRData
import eu.ruimgreis.testauto.logs.printTCFData
import eu.ruimgreis.testauto.model.SDKDefaults
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.DEBUG_NETWORK_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.ERROR_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.LOG_TAG
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.controllerID
import java.time.LocalDateTime

fun checkConnection(appContext: Context): Boolean{
    val connMgr = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var isWifiConn: Boolean = false
    var isMobileConn: Boolean = false
    connMgr.allNetworks.forEach { network ->
        connMgr.getNetworkInfo(network)!!.apply {
            if (type == ConnectivityManager.TYPE_WIFI) {
                isWifiConn = isWifiConn or isConnected
            }
            if (type == ConnectivityManager.TYPE_MOBILE) {
                isMobileConn = isMobileConn or isConnected
            }
        }
    }
    Log.d(DEBUG_NETWORK_TAG, "Wifi connected: $isWifiConn")
    Log.d(DEBUG_NETWORK_TAG, "Mobile connected: $isMobileConn")

    return isWifiConn
}

@RequiresApi(Build.VERSION_CODES.O)
fun restoreSession(){
    Usercentrics.isReady({ _ ->
        if(!controllerID.isNullOrEmpty()) {
            Usercentrics.instance.restoreUserSession(controllerID!!, { status ->
                Log.d(LOG_TAG, "ControllerId: ${Usercentrics.instance.getControllerId()}")
                printGDPRData(status.consents)
                Usercentrics.instance.getTCFData {
                    printTCFData()
                }
            }, { error ->
                Log.e(LOG_TAG, error.message!!)
            })
        }

    }, {
        //updateStateAndNotify()
        Log.d("[${LocalDateTime.now()}]", "UCS: error msdk1279")
    })
}

private fun reencodeTCString() {
    val tcStringMaxSize = 5000
    Usercentrics.isReady({
        Usercentrics.instance.getTCFData {
            val tcString = it.tcString
            if (tcString.length > tcStringMaxSize) {
                val cmpId = Usercentrics.instance.getCMPData().settings.tcf2?.cmpId
                if (cmpId != null)
                    Usercentrics.instance.setCMPId(cmpId)
            }
        }
    }, {
        Log.d(ERROR_TAG, "Error: ${it.message}")
    })
}

fun printDateTime(){

}

fun getVariant(): UsercentricsVariant{
    return Usercentrics.instance.getCMPData().activeVariant
}

fun getSettingsId(): String? {
    return when(getVariant()) {
        UsercentricsVariant.CCPA -> SDKDefaults.ccpaSettingsId
        UsercentricsVariant.TCF -> SDKDefaults.tcfSettingsId
        UsercentricsVariant.DEFAULT -> SDKDefaults.gdprSettingsId
        else -> {
            Log.d(SDKDefaults.ERROR_TAG, "Unknown UsercentricsVariant being used.")
            return null
        }
    }
}




