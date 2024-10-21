package com.blogspot.agusticar.miscuentasv2.about

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.components.HeadSetting
import com.blogspot.agusticar.miscuentasv2.components.RowComponent
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette

@Composable

fun AboutScreen(mainViewModel: MainViewModel)
{
    val message= stringResource(id = R.string.share)
    val context = LocalContext.current
    val appClimgingCompanionLink =
        "https://play.google.com/store/apps/details?id=com.blogspot.agusticar.climbcompanion&pli=1"
    val appMisCuentasLink =
        "https://play.google.com/store/apps/details?id=carnerero.agustin.cuentaappandroid&hl=es&gl=US"
    val policyLink =
        "https://climbingcompanion.blogspot.com/p/politica-de-privacidad-fecha-de-entrada.html"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {
        HeadSetting(title = stringResource(id = R.string.aboutapp), 20)

        RowComponent(title = stringResource(id = R.string.about),
            description = stringResource(id = R.string.desaboutapp),
            iconResource = R.drawable.info,
            onClick = {
                mainViewModel.selectScreen(IconOptions.ABOUT_DESCRIPTION)
            })
        RowComponent(title = stringResource(id = R.string.share),
            description = stringResource(id = R.string.desshare),
            iconResource = R.drawable.share,
            onClick = { shareLinkGooglePlayStore(context,appMisCuentasLink,message) })
        RowComponent(title = stringResource(id = R.string.rate),
            description = stringResource(id = R.string.desrate),
            iconResource = R.drawable.star_rate,
            onClick = { openGooglePlayStore(context,appMisCuentasLink) })
        RowComponent(title = stringResource(id = R.string.contactme),
            description = stringResource(id = R.string.desemail),
            iconResource = R.drawable.email,
            onClick = {mainViewModel.selectScreen(IconOptions.EMAIL) })
        RowComponent(title = stringResource(id = R.string.othersapp),
            description = stringResource(id = R.string.desstore),
            iconResource = R.drawable.apps,
            onClick = { openGooglePlayStore(context,appClimgingCompanionLink) })
        RowComponent(title = stringResource(id = R.string.privacy),
            description = stringResource(id = R.string.despolicy),
            iconResource = R.drawable.privacy,
            onClick = { openGooglePlayStore(context,policyLink) })
    }
}
@Composable
@Preview(showBackground = true)

fun AboutApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 25.dp)
            .verticalScroll(
                rememberScrollState()
            )
    )
    {
    HeadSetting(title = stringResource(id = R.string.app_name), 20)
    Text(
        text = stringResource(id = R.string.description),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 20.dp, start = 20.dp),
        fontSize = 18.sp,
        color = LocalCustomColorsPalette.current.textColor
    )
}
}

@Composable
fun SendEmail() {
    val context = LocalContext.current
    val sendEmailIntent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:agusticar@gmail.com") // Configurar el correo del destinatario
    }

    context.startActivity(sendEmailIntent) // Iniciar el correo electrónico

}

private fun shareLinkGooglePlayStore(context: Context, link: String, msg: String) {
    // Combinar el mensaje y el enlace en una sola cadena
    val combinedMsg = "$msg\n$link"

    // Crear un Intent para compartir
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = "text/plain"
    shareIntent.putExtra(Intent.EXTRA_TEXT, combinedMsg)

    // Iniciar un Activity chooser para que el usuario seleccione la aplicación con la que desea compartir el enlace
    context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share)))
}

private fun openGooglePlayStore(context: Context, link: String) {

    context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))

}


