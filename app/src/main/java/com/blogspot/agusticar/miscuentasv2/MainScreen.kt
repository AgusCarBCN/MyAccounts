package com.blogspot.agusticar.miscuentasv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.model.OptionItem
import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainScreen : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MisCuentasv2Theme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                Scaffold(modifier = Modifier.fillMaxSize(),
                    { MyTopAppBar(scope, drawerState) }

                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        //LoginComponent()
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(scope: CoroutineScope, drawerState: DrawerState) {
    ModalNavigationDrawer(

        drawerState = drawerState,
        drawerContent = { DrawerContent() },
        scrimColor = Color.Transparent,
        content = {
            // Main content goes here
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = stringResource(id = R.string.app_name)) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    if(drawerState.isOpen)Icons.Filled.Close else Icons.Filled.Menu ,
                                    contentDescription = "Side menu",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = colorResource(id = R.color.darkOrange),
                            titleContentColor = Color.White,
                            actionIconContentColor = colorResource(id = R.color.darkOrange)
                        ),
                        actions = {
                            // Additional actions can be added here
                            IconButton(onClick = { /* Additional action */ }) {
                                // Icon(Icons.Filled.Settings, contentDescription = "Settings", tint = Color.White)
                            }
                        }
                    )
                }
            ) { innerPadding ->
                // Add your main screen content here
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // Example content
                    Text("Welcome to the main content area!")
                    // You can add more components here
                }
            }
        }
    )
}


//Implementacion de Menú de la izquierda
@Composable
fun DrawerContent() {

    Card(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(top = 88.dp)
            .background(color = Color.Transparent)

    ) {

        HeadDrawerMenu()
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.lightYellow))
        ) {

            TitleOptions(R.string.management)
            ClickableRow(OptionItem(R.string.search,R.drawable.searchoption), onClick = {                
            })
            ClickableRow(OptionItem(R.string.calculator,R.drawable.ic_calculate), onClick = {})
            ClickableRow(OptionItem(R.string.newincome,R.drawable.paymentsoption), onClick = {})
            ClickableRow(OptionItem(R.string.transfer,R.drawable.transferoption), onClick = {})
            ClickableRow(OptionItem(R.string.chart,R.drawable.barchartoption), onClick = {})
            TitleOptions(R.string.others)
            ClickableRow(OptionItem(R.string.settings,R.drawable.settings), onClick = {})
            ClickableRow(OptionItem(R.string.about,R.drawable.info), onClick = {})
            ClickableRow(OptionItem(R.string.exitapp,R.drawable.exitapp), onClick = {})
        }
    }
}
//Implementacion de la cabecerera del menu desplegable izquierda
@Composable
private fun HeadDrawerMenu() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.orange))

    ) {
        Image(
            painterResource(id = R.drawable.contabilidad),
            contentDescription = "Side menu",
            modifier = Modifier
                .size(85.dp)
                .padding(10.dp)
        )

    }
}

// Implementación de Row clickable para cada opción del menú de la izquierda
@Composable
private fun ClickableRow(option:OptionItem,onClick: () -> Unit) {

        Row(
            modifier = Modifier
                .clickable(onClick = onClick) // Hacer el Row clickable
                .padding(16.dp)// Agregar padding para hacer más fácil el click
                .fillMaxWidth(), Arrangement.Start,
            Alignment.CenterVertically
        ) {
            Icon(
                painterResource(id = option.resourceIconItem),
                contentDescription = "Side menu",
                Modifier.size(28.dp),
                tint= colorResource(id = R.color.darkBrown)

            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = stringResource(id =option.resourceTitleItem),
                 color = colorResource(id = R.color.darkBrown),
                fontWeight = FontWeight.Bold
            )
        }
    }
@Composable
private fun TitleOptions(title:Int){
    Text(
        text = stringResource(id = title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ,
        color = Color.Black,
         //fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_large).toSp() },
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp

    )
}
