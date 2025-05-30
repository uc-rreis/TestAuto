package eu.ruimgreis.testauto

import androidx.test.espresso.Espresso
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test
import org.junit.Assert.*

class FirstLayerTestsGDPR {

    @Test
    fun clickAcceptAll() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("eu.ruimgreis.testauto", appContext.packageName)
    }
}