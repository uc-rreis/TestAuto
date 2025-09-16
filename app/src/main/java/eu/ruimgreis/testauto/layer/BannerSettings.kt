package eu.ruimgreis.testauto.layer

import android.content.Context
import android.graphics.Color
import android.graphics.Color.parseColor
import android.graphics.Typeface
import android.util.Log
import com.usercentrics.sdk.BannerFont
import com.usercentrics.sdk.BannerSettings
import com.usercentrics.sdk.ButtonLayout
import com.usercentrics.sdk.ButtonSettings
import com.usercentrics.sdk.ButtonType
import com.usercentrics.sdk.FirstLayerStyleSettings
import com.usercentrics.sdk.GeneralStyleSettings
import com.usercentrics.sdk.SecondLayerStyleSettings
import com.usercentrics.sdk.ToggleStyleSettings
import com.usercentrics.sdk.Usercentrics
import com.usercentrics.sdk.UsercentricsLayout
import com.usercentrics.sdk.firstLayerDescription
import com.usercentrics.sdk.v2.settings.data.UsercentricsSettings
import eu.ruimgreis.testauto.model.SDKDefaults
import eu.ruimgreis.testauto.model.SDKDefaults.Companion.BANNER_SETTINGS_TAG
import androidx.core.graphics.toColorInt
import com.usercentrics.sdk.MessageSettings
import com.usercentrics.sdk.TitleSettings

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

    return  bannerSettings  //
}

private fun setGeneralStyleSettings(context: Context): GeneralStyleSettings {
    //val poppins = ResourcesCompat.getFont(context, R.font.poppins)!! // Font from internet + xml
    val tmr = Typeface.createFromAsset(context.assets, "times_new_roman.ttf")
    val chalkboard = Typeface.createFromAsset(context.assets, "Chalkboard-Regular.ttf")
    val lora = Typeface.createFromAsset(context.assets, "Lora.ttf")
    return GeneralStyleSettings(
        textColor = null, //Color.parseColor("#F4FFF2F4"), //"#FF9DCB9A".toColorInt(), //Color.BLACK,
        layerBackgroundColor = null, //Color.parseColor("#9AFF9DCB"), //.toColorInt(), //Color.CYAN,
        layerBackgroundSecondaryColor = null, //"#9AFF9DCB".toColorInt(), //Color.RED,
        linkColor = null, //"#9AFF9DCB".toColorInt(), //Color.BLUE,
        tabColor = null, //"#9AFF9DCB".toColorInt(), //Color.MAGENTA,
        bordersColor = null, //"#45FF3740".toColorInt(), //Color.GREEN,
        toggleStyleSettings = ToggleStyleSettings(
            activeBackgroundColor = null, //"#12FF1212".toColorInt(), //Color.LTGRAY,
            inactiveBackgroundColor = null, //Color.DKGRAY,
            disabledBackgroundColor = null, //Color.MAGENTA,
            activeThumbColor = null, //Color.GREEN,
            inactiveThumbColor = null, //Color.RED,
            disabledThumbColor = null, //Color.YELLOW
        ),
        font = null, //BannerFont(context, chalkboard, 20f),
        logo = null,
        links = null, //LegalLinksSettings.HIDDEN
        disableSystemBackButton = false,
        statusBarColor = null, //Color.RED,
        windowFullscreen = null, //true
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
        title = null, //TitleSettings(textColor = "#008bff".toColorInt()),
        message = null, //MessageSettings(textColor = "#00ff5a".toColorInt(), linkTextColor = Color.RED),
        backgroundColor = null,
        buttonLayout = null, //ButtonLayout.Row(buttons), //ButtonLayout.Column(buttons),
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

private fun abTesting(context: Context): BannerSettings {
    val variant = Usercentrics.instance.getABTestingVariant()
    Log.d(SDKDefaults.VARIANT_TAG, "$variant")
    val bannerSettings = when (variant) {
        "variantA" -> BannerSettings()
        else -> BannerSettings(
            //font = <UsercentricsFont?>,
            //logo = <UsercentricsImage?>
            generalStyleSettings = setGeneralStyleSettings(context),
            firstLayerStyleSettings = setFirstLayerStyleSettings(UsercentricsLayout.Full),
            secondLayerStyleSettings = setSecondLayerStyleSettings()
        )
    }
    return bannerSettings
}

fun printBannerMessages(settings: UsercentricsSettings) {
    Log.d(BANNER_SETTINGS_TAG,"-- BANNER MESSAGE SPANNED -- ")
    val message = settings.firstLayerDescription
    Log.d(BANNER_SETTINGS_TAG, "$message")

    Log.d(BANNER_SETTINGS_TAG,"-- CLEANED BANNER MESSAGE SPANNED -- ")
    val messageClean = settings.firstLayerDescription.replace("[\n]+".toRegex(), "\n")
    Log.d(BANNER_SETTINGS_TAG, messageClean)

    Log.d(BANNER_SETTINGS_TAG,"-- BANNER MESSAGE HTML-- ")
    Log.d(BANNER_SETTINGS_TAG, "${settings.firstLayerDescriptionHtml}")

}