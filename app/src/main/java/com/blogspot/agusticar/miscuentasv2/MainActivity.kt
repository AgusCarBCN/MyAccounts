package com.blogspot.agusticar.miscuentasv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blogspot.agusticar.miscuentasv2.createaccounts.CreateAccountsComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.login.LoginComponent
import com.blogspot.agusticar.miscuentasv2.login.LoginViewModel
import com.blogspot.agusticar.miscuentasv2.main.model.Routes
import com.blogspot.agusticar.miscuentasv2.main.view.HomeScreen
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.tutorial.view.Tutorial
import com.blogspot.agusticar.miscuentasv2.tutorial.view.TutorialViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val createProfileViewModel: CreateProfileViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()


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
                        // Safely observe the LiveData values with default fallbacks
                        val showTutorial by tutorialViewModel.showTutorial.observeAsState(true) // Defaults to `true`
                        val toLogin by tutorialViewModel.toLogin.observeAsState(false) // Defaults to `false`
                        NavHost(
                            navController = navigationController,
                            startDestination = if(showTutorial)Routes.Tutorial.route
                            else Routes.Login.route

                        ) {
                            composable(Routes.Tutorial.route) {
                                Tutorial(
                                    tutorialViewModel,
                                    navToScreen = {navigationController.navigate(if(toLogin) Routes.Login.route
                                    else Routes.CreateProfile.route)}
                                )
                            }

                            composable(Routes.CreateProfile.route) {
                                CreateProfileComponent(createProfileViewModel,
                                    navToBackLogin =  {navigationController.popBackStack() },
                                    navToCreateAccounts = {navigationController.navigate(Routes.CreateAccounts.route)} )
                            }

                            composable(Routes.CreateAccounts.route) {
                                CreateAccountsComponent(navToLogin = {
                                    navigationController.navigate(Routes.Login.route)},
                                    navToBack = {navigationController.popBackStack()}
                                    )

                            }
                            composable(Routes.Login.route) {
                                LoginComponent(
                                    loginViewModel,
                                    modifier = Modifier.fillMaxSize(),
                                    navToMain = {
                                        navigationController.navigate(Routes.Home.route)}
                                )
                            }
                            composable(Routes.Home.route) {
                                HomeScreen(navigationController,mainViewModel)

                            }


                        }

                    }
                }
            }
        }
    }
}

