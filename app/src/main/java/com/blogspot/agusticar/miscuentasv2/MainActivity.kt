package com.blogspot.agusticar.miscuentasv2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsComponent
import com.blogspot.agusticar.miscuentasv2.createaccounts.view.CreateAccountsViewModel
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileComponent
import com.blogspot.agusticar.miscuentasv2.createprofile.CreateProfileViewModel
import com.blogspot.agusticar.miscuentasv2.login.LoginComponent
import com.blogspot.agusticar.miscuentasv2.login.LoginViewModel
import com.blogspot.agusticar.miscuentasv2.main.data.datastore.preferences.UserPreferencesKeys
import com.blogspot.agusticar.miscuentasv2.main.domain.datastoreusecase.GetShowTutorialUseCase
import com.blogspot.agusticar.miscuentasv2.main.model.Routes
import com.blogspot.agusticar.miscuentasv2.main.model.UserProfile
import com.blogspot.agusticar.miscuentasv2.main.view.HomeScreen
import com.blogspot.agusticar.miscuentasv2.main.view.MainViewModel
import com.blogspot.agusticar.miscuentasv2.setting.SettingViewModel
import com.blogspot.agusticar.miscuentasv2.tutorial.view.Tutorial
import com.blogspot.agusticar.miscuentasv2.tutorial.view.TutorialViewModel
import com.blogspot.agusticar.miscuentasv2.ui.theme.MisCuentasv2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val tutorialViewModel: TutorialViewModel by viewModels()
    private val createProfileViewModel: CreateProfileViewModel by viewModels()
    private val createAccountViewModel: CreateAccountsViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val settingViewModel: SettingViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            MisCuentasv2Theme {
                    val snackbarHostState= remember{
                        SnackbarHostState()
                    }
                val navigationController = rememberNavController()
                // Safely observe the LiveData values with default fallbacks

                val toLogin by tutorialViewModel.toLogin.observeAsState(false) // Defaults to `false`
                val showTutorial by tutorialViewModel.showTutorial.observeAsState(true)
                val scope = rememberCoroutineScope()

                Log.d("showTutorialMain", showTutorial.toString())
                ObserveAsEvents(
                    flow = SnackBarController.events,
                    snackbarHostState
                ) { event ->
                    scope.launch {
                        snackbarHostState.currentSnackbarData?.dismiss()

                        val result = snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = event.action?.name,
                            duration = SnackbarDuration.Short
                        )

                        if(result == SnackbarResult.ActionPerformed) {
                            event.action?.action?.invoke()
                        }
                    }
                }
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState)

                        }

                    ) {innerPadding->
                        NavHost(
                            navController = navigationController,
                            startDestination = if(showTutorial)Routes.Tutorial.route
                            else Routes.Login.route

                        ) {
                            composable(Routes.Tutorial.route) {
                                Tutorial(
                                    tutorialViewModel,
                                    navToScreen = {navigationController.navigate(if(toLogin) Routes.Login.route
                                    else Routes.CreateProfile.route)},
                                    modifier=Modifier.padding(innerPadding),
                                )
                            }

                            composable(Routes.CreateProfile.route) {
                                CreateProfileComponent(createProfileViewModel,
                                    navToBackLogin =  {navigationController.popBackStack() },
                                    navToCreateAccounts = {navigationController.navigate(Routes.CreateAccounts.route)} )
                            }

                            composable(Routes.CreateAccounts.route) {
                                CreateAccountsComponent(createAccountViewModel,navToLogin = {
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
                                HomeScreen(navigationController,
                                    mainViewModel,
                                    createAccountViewModel,
                                    createProfileViewModel,
                                    tutorialViewModel,
                                    settingViewModel)

                            }


                        }

                    }
                }
            }
        }
    }


