package eu.ruimgreis.testauto.layer

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import com.usercentrics.sdk.BannerFont
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.ButtonSettings
import com.usercentrics.sdk.ButtonType
import com.usercentrics.sdk.FirstLayerStyleSettings
import com.usercentrics.sdk.GeneralStyleSettings
import com.usercentrics.sdk.SecondLayerStyleSettings
import com.usercentrics.sdk.ToggleStyleSettings
import com.usercentrics.sdk.UsercentricsLayout
import com.usercentrics.sdk.firstLayerDescription
import com.usercentrics.sdk.v2.settings.data.UsercentricsSettings
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.BANNER_SETTINGS_TAG

fun getBannerSettings(context: Context, popup: UsercentricsLayout?): BannerSettings {
    val firstLayerSettings = setFirstLayerStyleSettings(popup)
    val secondLayerSettings = setSecondLayerStyleSettings()
    val generalStyleSettings = setGeneralStyleSettings(context)

    val bannerSettings = BannerSettings(
        //font = <UsercentricsFont?>,
        //logo = <UsercentricsImage?>
        generalStyleSettings = generalStyleSettings,
        firstLayerStyleSettings = firstLayerSettings,
        secondLayerStyleSettings = secondLayerSettings
    )

    return bannerSettings
}

private fun setGeneralStyleSettings(context: Context): GeneralStyleSettings {
    //val poppins = ResourcesCompat.getFont(context, R.font.poppins)!! // Font from internet + xml
    val tmr = Typeface.createFromAsset(context.assets, "times_new_roman.ttf")
    val lora = Typeface.createFromAsset(context.assets, "Lora.ttf")
    return GeneralStyleSettings(
        textColor = null, //Color.BLACK,
        layerBackgroundColor = null, //Color.CYAN,
        layerBackgroundSecondaryColor = null, //Color.RED,
        linkColor = null, //Color.BLUE,
        tabColor = null, //Color.MAGENTA,
        bordersColor = null, //Color.GREEN,
        toggleStyleSettings = ToggleStyleSettings(
            activeBackgroundColor = null, //Color.LTGRAY,
            inactiveBackgroundColor = null, //Color.DKGRAY,
            disabledBackgroundColor = null, //Color.MAGENTA,
            activeThumbColor = null, //Color.GREEN,
            inactiveThumbColor = null, //Color.RED,
            disabledThumbColor = null, //Color.YELLOW
        ),
        font = null, //BannerFont(context, tmr, 20f),
        logo = null,
        links = null, //LegalLinksSettings.HIDDEN
        //disableSystemBackButton = true
        windowFullscreen = true
    )
}

private fun setFirstLayerStyleSettings(popup: UsercentricsLayout?): FirstLayerStyleSettings {
    // Applies to First and Second Layer, and overwrites Admin Interface Styling Settings
//    val bannerImage =
//        HeaderImageSettings.ExtendedLogoSettings(UsercentricsImage.ImageDrawableId(R.drawable.banner))
    val acceptButton = ButtonSettings(
        ButtonType.ACCEPT_ALL,
        textColor = Color.RED,
        backgroundColor = Color.GREEN,
        isAllCaps = true
    )
    val denyButton = ButtonSettings(
        ButtonType.DENY_ALL,
        textColor = Color.WHITE,
        backgroundColor = Color.BLACK,
        isAllCaps = true)
    val moreButton = ButtonSettings(
        ButtonType.MORE,
        textColor = Color.WHITE,
        backgroundColor = Color.MAGENTA,
        isAllCaps = true)
    val saveButton = ButtonSettings(
        ButtonType.SAVE,
        textColor = Color.BLUE,
        backgroundColor = Color.YELLOW,
        isAllCaps = true)
    val buttons = listOf<ButtonSettings>(acceptButton, denyButton,saveButton)

    // Applies to First Layer, and overwrites General Settings
    return FirstLayerStyleSettings(
        layout = popup,
        headerImage = null, //bannerImage,
        title = null,
        message = null,
        backgroundColor = null,
        buttonLayout = null, //ButtonLayout.Column(buttons),
        overlayColor = null,
        cornerRadius = null,
    )
}

private fun setSecondLayerStyleSettings(): SecondLayerStyleSettings {
    // Applies to Second Layer, and overwrites Admin Interface Styling Settings
    val acceptButton = ButtonSettings(ButtonType.ACCEPT_ALL)
    val denyButton = ButtonSettings(ButtonType.DENY_ALL)
    val buttons = listOf<ButtonSettings>(acceptButton, denyButton)

    // Applies to First Layer, and overwrites General Settings
    return SecondLayerStyleSettings(
        buttonLayout = null, //ButtonLayout.Column(buttons),
        showCloseButton = true
    )
}

//private fun abTesting(context: Context){
//    val variant = Usercentrics.instance.getABTestingVariant()
//    Log.d(SDKDefaults.VARIANT_TAG, "$variant")
//    val bannerSettings = when (variant) {
//        "variant0" -> BannerSettings()
//        else -> BannerSettings(
//            //font = <UsercentricsFont?>,
//            //logo = <UsercentricsImage?>
//            generalStyleSettings = setGeneralStyleSettings(context),
//            firstLayerStyleSettings = setFirstLayerStyleSettings(),
//            secondLayerStyleSettings = setSecondLayerStyleSettings()
//        )
//    }
//}

fun printBannerMessages(settings: UsercentricsSettings) {
    Log.d(BANNER_SETTINGS_TAG,"-- BANNER MESSAGE SPANNED -- ")
    val message = settings.firstLayerDescription
    Log.d(BANNER_SETTINGS_TAG, "$message")

    Log.d(BANNER_SETTINGS_TAG,"-- CLEANED BANNER MESSAGE SPANNED -- ")
    val messageClean = settings.firstLayerDescription.replace("[\n]+".toRegex(), "\n")
    Log.d(BANNER_SETTINGS_TAG, "$messageClean")

    Log.d(BANNER_SETTINGS_TAG,"-- BANNER MESSAGE HTML-- ")
    Log.d(BANNER_SETTINGS_TAG, "${settings.firstLayerDescriptionHtml}")

}