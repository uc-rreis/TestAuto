package eu.ruimgreis.testauto.links

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import eu.ruimgreis.testauto.BuildConfig


class DeepLinkActivity: AppCompatActivity() {

    // to be tested on settingsId hKTmJ4UVL with language "de" in UsercentricsOptions
    // the properties schemeName and hostName are set in gradle.properties
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CUSTOM_LINKS", "Deep Link Activity created")
        val data = intent?.data ?: return

        val schemeName = BuildConfig.schemeName
        val hostName = BuildConfig.hostName

        if (data.scheme == schemeName && data.host == hostName) {
            // Custom Implementation for Link
            Log.d("CUSTOM_LINKS", "${data.scheme} - ${data.host}")

            val browserIntent = Intent(Intent.ACTION_VIEW, "http://www.google.com".toUri())
            startActivity(browserIntent)
            finish()
        }
    }
}