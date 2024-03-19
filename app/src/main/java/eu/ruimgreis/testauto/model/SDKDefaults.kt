package eu.ruimgreis.testauto.model

class SDKDefaults {

    /**
     * Default values for properties in SDK
     * example settingsId = "egLMgjg9j"
     */

    companion object {
        // UC SDK vals
        const val gdprSettingsId: String = "egLMgjg9j"//"egLMgjg9j"
        const val tcfSettingsId: String = "hKTmJ4UVL"//"hKTmJ4UVL"
        const val ccpaSettingsId: String = "syV5G8hMG"
        const val rulesetId: String = "" //"YxCPTs3W9"

        val controllerID: String? = ""

        // LOG TAGSR
        val LOG_TAG = "[USERCENTRICS][SAMPLE]"
        val CONSENT_TAG = "[USERCENTRICS][CONSENTS]"
        val TCF_TAG = "[USERCENTRICS][TCF]"
        val TCSTRING_TAG = "[USERCENTRICS][TCSTRING]"
        val DEBUG_NETWORK_TAG = "[USERCENTRICS][NETWORK STATUS]"
        val CCPA_TAG = "[USERCENTRICS][CCPA]"
        val SHOULD_COLLECT_CONSENT_TAG = "[USERCENTRICS][SHOULD_COLLECT_CONSENT]"
        val ERROR_TAG = "[USERCENTRICS][ERROR]"
        val BANNER_SETTINGS_TAG = "[USERCENTRICS][BANNER_SETTINGS]"
        val VARIANT_TAG = "[USERCENTRICS][VARIANT]"
        val CMP_DATA_TAG = "[USERCENTRICS][CMPDATA]"
        val GAC_STRING_TAG = "[USERCENTRICS][AC_STRING]"
        val GAC_PROVIDERS_TAG =  "[USERCENTRICS][AC_PROVIDERS]"
        val PURPOSES_TAG = "[USERCENTRICS][PURPOSES]"
        val UNITY_ADS_TAG = "[USERCENTRICS][UADS]"
        val JS_LOG_TAG = "[UC][JSInterface]"
    }
}