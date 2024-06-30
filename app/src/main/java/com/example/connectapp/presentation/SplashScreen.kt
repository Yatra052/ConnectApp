package com.example.connectapp.presentation

import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.connectapp.R
import com.example.connectapp.navigation.Routes

import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navHostController: NavHostController)
{

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.phonne),
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp)
        )
    }

    LaunchedEffect(key1 = true) {

        delay(3000)

        navHostController.navigate(Routes.AllContact.route)
    }

}