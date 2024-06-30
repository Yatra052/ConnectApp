package com.example.connectapp.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.connectapp.navigation.Routes

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllContact(viewModel: ContactViewModel, state: ContactState, navController: NavController) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                modifier = Modifier
                    .background(Color.White)
                    .clickable {
                        viewModel.changeSorting()
                    },

                title = {
                    Icon(
                        imageVector = Icons.Rounded.Menu,
                        contentDescription = "rounded memu"
                    )
                })
        }, floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Routes.AddNewContact.route) }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(it)
        ) {

            items(state.contact) {
                var bitmap: Bitmap? = null
                if (it.image != null)
                    bitmap = BitmapFactory.decodeByteArray(it.image, 0, it.image!!.size)
                Card(
                    modifier = Modifier

                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 10.dp, vertical = 12.dp)
                ) {

                    Row {
                        bitmap?.asImageBitmap()
                            ?.let { it1 ->
                                Image(
                                    bitmap = it1, contentDescription = "null",
                                    modifier = Modifier.size(70.dp).padding(top = 20.dp)
                                        .clip(shape = CircleShape)

                                )
                            }
                        Column(modifier = Modifier.clickable {
                            state.id.value = it.id
                            state.number.value = it.number
                            state.name.value = it.name
                            state.email.value = it.email
                            state.dateOfCreation.value = it.dateOfCreation
                            navController.navigate(Routes.AddNewContact.route)
                        }) {

                            Text(text = it.name, modifier = Modifier.padding(start = 20.dp))
                            Text(text = it.number, modifier = Modifier.padding(start = 20.dp))
                            Text(text = it.email, modifier = Modifier.padding(start = 20.dp))


                        }

                        Icon(imageVector = Icons.Rounded.Delete, contentDescription = "del",
                            modifier = Modifier
                                .padding(start = 70.dp, top = 6.dp)
                                .clickable {
                                    state.id.value = it.id
                                    state.number.value = it.number
                                    state.name.value = it.name
                                    state.email.value = it.email
                                    state.dateOfCreation.value = it.dateOfCreation
                                    viewModel.deleteContact()
                                }
                        )

                        Icon(imageVector = Icons.Rounded.Phone, contentDescription = "del",
                            modifier = Modifier
                                .padding(start = 10.dp, top = 8.dp)
                                .clickable {
                                    val intent = Intent(Intent.ACTION_CALL)
                                    intent.data = Uri.parse("tel:${it.number}")
                                    context.startActivity(intent)
                                }
                        )



                    }


    }
            }
        }
    }
}

