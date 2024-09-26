package com.blogspot.agusticar.miscuentasv2.recyclerview


import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.blogspot.agusticar.miscuentasv2.R

import com.blogspot.agusticar.miscuentasv2.component.ModelButton
import com.blogspot.agusticar.miscuentasv2.model.TutorialItem

@Composable
fun Tutorial(modifier: Modifier = Modifier, listOfItems: List<TutorialItem> = getItems()) {



    ConstraintLayout(

        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.yellow))
    ) {
        val (lazyBox, loginButton) = createRefs()
        LazyRow(
            modifier.fillMaxWidth()
                .constrainAs(lazyBox) {
                    top.linkTo(parent.top)          // Parte superior anclada al imageBox
                    start.linkTo(parent.start)           // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)               // Termina en el lado derecho del padre
                    bottom.linkTo(loginButton.top)         // Parte inferior anclada al padre
                }

        ) {
            items(items = listOfItems) { item ->
                ItemCard(item)
            }
        }
        ModelButton(text = stringResource(id = R.string.loginButton),
            modifier = Modifier.width(360.dp)
                .constrainAs(loginButton) {
                    top.linkTo(lazyBox.bottom)          // Parte superior anclada al imageBox
                    start.linkTo(parent.start)           // Empieza en el lado izquierdo del padre
                    end.linkTo(parent.end)               // Termina en el lado derecho del padre
                    bottom.linkTo(parent.bottom)         // Parte inferior anclada al padre
                }
            , true,
            onClickButton = {

            }
        )
    }

}

@Composable
fun ItemCard(item: TutorialItem) {
    // Un card sencillo que muestra título, descripción e ícono
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()

    ) {
        Row {
            Column(
                modifier = Modifier.background(colorResource(id = R.color.yellow)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = item.titleItem,
                    modifier = Modifier
                        .width(400.dp)
                        .padding(top = 20.dp, bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.width(5.dp)) // Espacio entre imagen y texto
                // Mostrar imagen
                Image(
                    painter = painterResource(id = item.iconItem),
                    contentDescription = null, // No se requiere descripción accesible para imágenes decorativas
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .padding(top = 10.dp, bottom = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.width(5.dp)) // Espacio entre imagen y texto
                Text(
                    text = item.descriptionItem,
                    modifier = Modifier
                        .width(400.dp)
                        .height(200.dp)
                        .padding(top = 10.dp, bottom = 10.dp, end = 10.dp,start=15.dp)
                        .align(Alignment.CenterHorizontally),

                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )


            }

        }
    }
}


@Composable
private fun getItems(): List<TutorialItem> {
    // Obtener recursos de string usando stringResource
    return listOf(
        TutorialItem(
            title = stringResource(id = R.string.title0),
            description = stringResource(id = R.string.des0),
            icon = R.drawable.contabilidad
        ),
        TutorialItem(
            title = stringResource(id = R.string.title1),
            description = stringResource(id = R.string.des1),
            icon = R.drawable.person_intro
        ),
        TutorialItem(
            title = stringResource(id = R.string.title2),
            description = stringResource(id = R.string.des2),
            icon = R.drawable.payments_intro
        ),
        TutorialItem(
            title = stringResource(id = R.string.title3),
            description = stringResource(id = R.string.des3),
            icon = R.drawable.barchart_intro
        ),
        TutorialItem(
            title = stringResource(id = R.string.title4),
            description = stringResource(id = R.string.des4),
            icon = R.drawable.notifications_intro
        ),
        TutorialItem(
            title = stringResource(id = R.string.title5),
            description = stringResource(id = R.string.des5),
            icon = R.drawable.settings_intro
        )
    )
}



