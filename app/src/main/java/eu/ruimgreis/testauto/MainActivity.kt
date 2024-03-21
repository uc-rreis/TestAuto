package eu.ruimgreis.testauto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.usercentrics.sdk.PopupPosition
import com.usercentrics.sdk.UsercentricsLayout
import com.usercentrics.sdk.UsercentricsOptions
import eu.ruimgreis.testauto.init.initCMP
import eu.ruimgreis.testauto.layer.showCMP
import eu.ruimgreis.testauto.layer.showSecondLayer
import eu.ruimgreis.testauto.webview.WebviewActivity

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
fun App(){
    MaterialTheme {
        var ucId by remember { mutableStateOf("") }
        val context = LocalContext.current
        val rulesetChecked = remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val activity = LocalContext.current as Activity

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,

            // Image header
            ) {
            Image(
                painterResource(R.drawable.banner),
                null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            // RulesetId/SettingsId input
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedTextField(
                    value = ucId,
                    onValueChange = { ucId = it },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    label = { Text(stringResource(id = R.string.settings_id)) },
                    //placeholder = {Text("egLMgjg9j")},
                    modifier = Modifier.size(width = 200.dp, height = 60.dp)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = rulesetChecked.value,
                        onCheckedChange = { isChecked -> rulesetChecked.value = isChecked }
                    )
                    Text(
                            stringResource(id = R.string.checkbox_ruleset),
                            fontSize = 13.sp
                        )
                }
            }

            // Initialization button
            OutlinedButton(
                onClick = {
                    val settingsId = ucId.ifEmpty { "egLMgjg9j" }
                    keyboardController?.hide()
                    val options = UsercentricsOptions(settingsId)
                    if(rulesetChecked.value) {
                        options.ruleSetId = ucId
                    }
                    initCMP(context, options)
                },
                border = BorderStroke(color = Color.LightGray, width = 2.dp)
            ) {
                Text(stringResource(id = R.string.btn_init))
            }

            // Layer Buttons
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(onClick = {
                    showCMP(UsercentricsLayout.Full, context)
                }){
                    Text(stringResource(id = R.string.btn_full))
                }
                Button(onClick = {
                    showCMP(UsercentricsLayout.Popup(PopupPosition.CENTER), context)
                }){
                    Text(stringResource(id = R.string.btn_first_center))
                }
                Button(onClick = {
                    showCMP(UsercentricsLayout.Popup(PopupPosition.BOTTOM), context)
                }){
                    Text(stringResource(id = R.string.btn_first_bottom))
                }
                Button(onClick = {
                    showCMP(UsercentricsLayout.Sheet, context)
                }){
                    Text(stringResource(id = R.string.btn_first_sheet))
                }
                Button(onClick = {
                    showSecondLayer(context)
                }){
                    Text(stringResource(id = R.string.btn_second_layer))
                }

                Button(onClick = {
                    context.startActivity(Intent(context, WebviewActivity::class.java))
                }){
                    Text(stringResource(id = R.string.btn_open_webview))
                }
            }
        }
    }
}