package com.blogspot.agusticar.miscuentasv2.model

sealed class Routes(val route:String){
    object Home : Routes("home")
    object CreateProfile : Routes("create profile")
    object CreateAccounts : Routes("create accounts")
    object Tutorial : Routes("tutorial")
    object Login : Routes("login")  // Add more routes as needed

}