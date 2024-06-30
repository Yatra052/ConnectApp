package com.example.connectapp.navigation

sealed class Routes(val route:String){

    object AllContact: Routes("AllContact")
    object AddNewContact: Routes("AddNewContact")
    object SplashScreen:Routes("SplashScreen")





}