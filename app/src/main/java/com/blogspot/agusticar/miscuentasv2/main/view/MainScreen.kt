package com.blogspot.agusticar.miscuentasv2.main.view


import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.blogspot.agusticar.miscuentasv2.R
import com.blogspot.agusticar.miscuentasv2.about.AboutApp
import com.blogspot.agusticar.miscuentasv2.about.AboutScreen
import com.blogspot.agusticar.miscuentasv2.components.CurrencySelector
import com.blogspot.agusticar.miscuentasv2.components.IconComponent
import com.blogspot.agusticar.miscuentasv2.components.UserImage
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.home.HomeScreen
import com.blogspot.agusticar.miscuentasv2.main.model.IconOptions
import com.blogspot.agusticar.miscuentasv2.newamount.CategorySelector
import com.blogspot.agusticar.miscuentasv2.newamount.NewAmount
import com.blogspot.agusticar.miscuentasv2.profile.ProfileScreen
import com.blogspot.agusticar.miscuentasv2.setting.SettingScreen
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.blogspot.agusticar.miscuentasv2.transfer.Transfer
import com.blogspot.agusticar.miscuentasv2.tutorial.model.OptionItem
import com.blogspot.agusticar.miscuentasv2.ui.theme.LocalCustomColorsPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Composable
fun MainScreen(

    mainViewModel: MainViewModel,
    createAccountsViewModel: CreateAccountsViewModel,
    createProfileViewModel: CreateProfileViewModel,
    settingViewModel:SettingViewModel

) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedScreen by mainViewModel.selectedScreen.collectAsState()
    val iconAmount by mainViewModel.selectedIcon.collectAsState()
    val titleAmount by mainViewModel.selectedTitle.collectAsState()
    val isIncome by mainViewModel.isIncome.collectAsState()

    val categories by createAccountsViewModel.listOfCategories.observeAsState(listOf())

    val userName by createProfileViewModel.name.observeAsState("")
    var title: Int by remember{ mutableIntStateOf(R.string.hometitle)}

    // Usar LaunchedEffect para cerrar el drawer cuando cambia la pantalla seleccionada
    LaunchedEffect(key1 = selectedScreen) {
        if (drawerState.isOpen) {
            drawerState.close() // Cierra el drawer cuando se selecciona una opción
        }

    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { DrawerContent(mainViewModel, createProfileViewModel) },
        scrimColor = Color.Transparent,
        content = {
            // Main content goes here
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                { TopBarApp(scope, drawerState,title,
                    (if(selectedScreen==IconOptions.HOME) userName else "").toString()
                ) },
                { BottomAppBar(mainViewModel) },
                containerColor = LocalCustomColorsPalette.current.backgroundPrimary
            ) { innerPadding ->
                // Add your main screen content here
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {if(selectedScreen!=IconOptions.EXIT){
                    createProfileViewModel.onButtonProfileNoSelected()
                }
                    when (selectedScreen) {
                        IconOptions.HOME -> {
                            HomeScreen(createAccountsViewModel)
                            title=R.string.greeting
                        }
                        IconOptions.PROFILE -> {ProfileScreen(createProfileViewModel)
                            title=R.string.profiletitle
                        }
                        IconOptions.SEARCH -> TODO()
                        IconOptions.SETTINGS -> {
                            SettingScreen(settingViewModel,mainViewModel)
                            title=R.string.settingstitle}
                        IconOptions.INCOME_OPTIONS -> {
                            LaunchedEffect(Unit) {
                                createAccountsViewModel.getCategories(true)

                            }
                            CategorySelector(mainViewModel,categories,true)
                        title=R.string.newincome}
                        IconOptions.TRANSFER -> {Transfer()
                        title=R.string.transfer}
                        IconOptions.BARCHART -> TODO()
                        IconOptions.CALCULATOR -> TODO()
                        IconOptions.SETTING_ACCOUNTS -> TODO()
                        IconOptions.ABOUT ->{ AboutScreen(mainViewModel)
                            title=R.string.abouttitle
                        }
                        IconOptions.POLICY -> TODO()
                        IconOptions.EXIT -> {
                            // Obtén el contexto actual de la aplicación
                            val context = LocalContext.current
                            // Verifica si el contexto es una actividad
                            val activity = context as? Activity
                            activity?.finish()
                        }
                        IconOptions.ABOUT_DESCRIPTION -> {
                            AboutApp()
                            title=R.string.abouttitle
                        }
                        IconOptions.EXPENSE_OPTIONS -> {
                            LaunchedEffect(Unit) {
                                createAccountsViewModel.getCategories(false)

                            }
                            CategorySelector(mainViewModel,categories,false)


                            title=R.string.newexpense
                        }

                        IconOptions.NEW_AMOUNT -> {
                            NewAmount(isIncome, iconAmount ,titleAmount)
                            title=titleAmount
                        }

                        IconOptions.CHANGE_CURRENCY -> CurrencySelector(createAccountsViewModel)
                    }

                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarApp(scope: CoroutineScope, drawerState: DrawerState,title:Int,name:String) {

    TopAppBar(
        title = { Text(text = stringResource(id = title)+" "+name)},
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(
                    if (drawerState.isOpen) Icons.Filled.Close else Icons.Filled.Menu,
                    contentDescription = "Side menu",
                    tint = LocalCustomColorsPalette.current.topBarContent
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LocalCustomColorsPalette.current.barBackground,
            titleContentColor = LocalCustomColorsPalette.current.topBarContent,
            actionIconContentColor = LocalCustomColorsPalette.current.topBarContent
        )
    )
}


@Composable
private fun BottomAppBar(viewModel: MainViewModel) {

    BottomAppBar(
        containerColor = LocalCustomColorsPalette.current.barBackground,
        contentColor = LocalCustomColorsPalette.current.topBarContent,
        actions = {
            IconButtonApp("Home",
                R.drawable.home,
                onClickButton = { viewModel.selectScreen(IconOptions.HOME) })
            Spacer(modifier = Modifier.weight(1f, true)) // Espacio entre íconos
            IconButtonApp("Search", R.drawable.search, onClickButton = {})
            Spacer(modifier = Modifier.weight(1f, true)) // Espacio entre íconos
            IconButtonApp("Settings", R.drawable.settings,
                onClickButton = { viewModel.selectScreen(IconOptions.SETTINGS) })
            Spacer(modifier = Modifier.weight(1f, true)) // Espacio entre íconos
            IconButtonApp("Profile", R.drawable.profile, onClickButton = {
                viewModel.selectScreen(IconOptions.PROFILE)

            })
        },
        tonalElevation = 5.dp
    )
}


//Implementacion de Menú de la izquierda
@Composable
private fun DrawerContent(
    viewModel: MainViewModel,
    createProfileViewModel: CreateProfileViewModel
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .padding(top = 88.dp)
            .background(color = Color.Transparent)

    ) {

        HeadDrawerMenu(createProfileViewModel)
        Column(
            modifier = Modifier
                .background(LocalCustomColorsPalette.current.drawerColor)
        ) {
            TitleOptions(R.string.management)
            ClickableRow(OptionItem(R.string.newincome, R.drawable.incomes), onClick = {
                viewModel.selectScreen(IconOptions.INCOME_OPTIONS)
            })
            ClickableRow(OptionItem(R.string.newexpense, R.drawable.importoption), onClick = {
                viewModel.selectScreen(IconOptions.EXPENSE_OPTIONS)
            })
            ClickableRow(OptionItem(R.string.transfer, R.drawable.transferoption), onClick = {
                viewModel.selectScreen(IconOptions.TRANSFER)
            })
            ClickableRow(OptionItem(R.string.chart, R.drawable.barchartoption), onClick = {})
            ClickableRow(OptionItem(R.string.calculator, R.drawable.ic_calculate), onClick = {})
            TitleOptions(R.string.aboutapp)
            ClickableRow(OptionItem(R.string.about, R.drawable.info), onClick = {
                viewModel.selectScreen(IconOptions.ABOUT)
            })
            ClickableRow(
                OptionItem(R.string.exitapp, R.drawable.exitapp),
                onClick = { viewModel.selectScreen(IconOptions.EXIT) })
        }
    }
}

//Implementacion de la cabecerera del menu desplegable izquierda
@Composable
fun HeadDrawerMenu(createProfileViewModel: CreateProfileViewModel) {

    val selectedImageUriSaved by createProfileViewModel.selectedImageUriSaved.observeAsState(null)


    createProfileViewModel.loadImageUri()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(LocalCustomColorsPalette.current.headDrawerColor),
                Arrangement.SpaceEvenly,
        Alignment.CenterVertically


    ) {
        Box(modifier = Modifier.weight(0.4f)) {
        selectedImageUriSaved?.let { UserImage(it,80) }
        }

<<<<<<< HEAD
<<<<<<< HEAD


        Column(modifier = Modifier.weight(0.6f),
=======
        /*Column(modifier = Modifier.weight(0.6f),
>>>>>>> develop
            ) {
            Text(text = "Hola",
                color= LocalCustomColorsPalette.current.textColor)
            Text(text = "$name !",
                color= LocalCustomColorsPalette.current.textColor)
        }*/


=======
>>>>>>> develop
    }

}


// Implementación de Row clickable para cada opción del menú de la izquierda
@Composable
private fun ClickableRow(
    option: OptionItem,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    // Detectar si la fila está presionada
    val isPressed by interactionSource.collectIsPressedAsState()

    // Definir el color de fondo dependiendo si está presionado o no
    val backgroundColor by animateColorAsState(
        targetValue = if (isPressed) LocalCustomColorsPalette.current.rowDrawerPressed
        else LocalCustomColorsPalette.current.drawerColor,
        label = "row clickable color"
    )
    val contentRowColor by animateColorAsState(
        targetValue = if (isPressed) LocalCustomColorsPalette.current.invertedTextColor
        else LocalCustomColorsPalette.current.contentBarColor,
        label = "row clickable color"
    )
    // Fila clickable con color de fondo animado
    Row(
        modifier = Modifier
            .clickable(
                onClick = { onClick() },
                interactionSource = interactionSource,
                indication = null // Esto elimina el efecto predeterminado de ripple
            )
            .background(backgroundColor) // Aplicar el color de fondo dinámico
            .padding(16.dp) // Agregar padding
            .fillMaxWidth(), // Ocupar todo el ancho disponible
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            painter = painterResource(id = option.resourceIconItem),
            contentDescription = "Side menu",
            modifier = Modifier.size(28.dp),
            tint = contentRowColor
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = stringResource(id = option.resourceTitleItem),
            color = contentRowColor,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
private fun TitleOptions(title: Int) {

    Text(
        text = stringResource(id = title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = LocalCustomColorsPalette.current.contentDrawerColor,
        //fontSize = with(LocalDensity.current) { dimensionResource(id = R.dimen.text_body_large).toSp() },
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp

    )
}


@Composable
private fun IconButtonApp(title: String, resourceIcon: Int, onClickButton: () -> Unit) {
// Creamos una fuente de interacciones para el IconButton
    val interactionSource = remember { MutableInteractionSource() }
    // Detectamos si el botón está presionado

    val isPressed by interactionSource.collectIsPressedAsState()

    IconButton(

        onClick = onClickButton,
        interactionSource = interactionSource
    ) {
        IconComponent(isPressed, resourceIcon, 28)
    }

}


