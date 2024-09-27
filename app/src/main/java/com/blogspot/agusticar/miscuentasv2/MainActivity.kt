package com.blogspot.agusticar.miscuentasv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MisCuentasv2Theme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                Scaffold(modifier = Modifier) { innerPadding ->

                   Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val navigationController = rememberNavController()
                        NavHost(
                            navController = navigationController,
                            startDestination = "createprofile"
                        ) {
                            composable("login"){
                                LoginComponent(modifier=Modifier.fillMaxSize() ,navigationController)
                            }
                        composable("createprofile"){
                            CreateProfileComponent(navigationController)
                        }
                        composable("home"){
                            HomeScreen(navigationController)
                        }
                        }
                    }

                }
            }
        }
    }

}

