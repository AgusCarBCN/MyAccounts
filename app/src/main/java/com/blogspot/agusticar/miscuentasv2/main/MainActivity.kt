package com.blogspot.agusticar.miscuentasv2.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blogspot.agusticar.miscuentasv2.createaccounts.CreateAccountsComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileComponent
import com.blogspot.agusticar.miscuentasv2.login.LoginComponent
import com.blogspot.agusticar.miscuentasv2.login.LoginViewModel
import com.blogspot.agusticar.miscuentasv2.main.view.HomeScreen
import com.blogspot.agusticar.miscuentasv2.main.model.Routes
import com.blogspot.agusticar.miscuentasv2.tutorial.view.Tutorial
import com.blogspot.agusticar.miscuentasv2.tutorial.view.TutorialViewModel

import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme

class MainActivity : ComponentActivity() {

   private val context=this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisCuentasv2Theme {

                Scaffold(modifier = Modifier) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navigationController = rememberNavController()
                        NavHost(
                            navController = navigationController,
                            startDestination =Routes.Tutorial.route

                        ) {
                            composable(Routes.Login.route) {
                                LoginComponent(
                                    modifier = Modifier.fillMaxSize(),
                                    navigationController, LoginViewModel()
                                )
                            }
                            composable(Routes.CreateProfile.route) {
                                CreateProfileComponent(navigationController)
                            }
                            composable(Routes.Home.route) {
                                HomeScreen(navigationController)
                            }
                            composable(Routes.Tutorial.route) {
                               Tutorial(TutorialViewModel(this@MainActivity),modifier= Modifier.padding(innerPadding),
                                   navigationController)
                            }
                            composable(Routes.CreateAccounts.route) {
                                CreateAccountsComponent(navigationController)
                            }
                        }

                    }
                }
            }
        }
    }
}

