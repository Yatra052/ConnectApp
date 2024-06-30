package com.example.connectapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.connectapp.presentation.AddEditScreen
import com.example.connectapp.presentation.AllContact
import com.example.connectapp.presentation.ContactViewModel
import com.example.connectapp.presentation.SplashScreen

@Composable
fun NavHostGraph(modifier: Modifier = Modifier, viewModel:ContactViewModel, navController: NavHostController)
{

    val state by viewModel.state.collectAsState()

    NavHost(navController = navController, startDestination = Routes.SplashScreen.route){

        composable(Routes.SplashScreen.route){
            SplashScreen(navHostController = navController)
        }
        composable(Routes.AllContact.route){
            AllContact(viewModel = viewModel, state = state, navController=navController)
        }

        composable(Routes.AddNewContact.route){

            AddEditScreen(state = viewModel.state.collectAsState().value, navController = navController) {
               viewModel.saveContact()
            }
        }
    }
}