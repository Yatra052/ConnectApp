package com.example.connectapp.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.connectapp.R
import java.io.ByteArrayOutputStream
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun AddEditScreen(
    state: ContactState,
    navController: NavController,
    onEvent:()->Unit

) {
    val context = LocalContext.current

//    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia()) {
//            uri: Uri? ->
//
//        if (uri != null)
//        {
//           val inputStream: InputStream? = uri.let {
//               context.contentResolver.openInputStream(it)
//           }
//
//
//            val byte = inputStream?.readBytes()
//            if (byte != null) {
//                state.image.value = byte!!
//            }
//        }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri != null) {
                val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
                val byte = inputStream?.readBytes()

                if (byte != null) {
                    state.image.value = byte

                    val compressedImage = compressImage(byte)
                    if (compressedImage.size > 1024 * 1024) { // 1MB
                        Toast.makeText(context, "Image size is too large. Please choose a smaller image.", Toast.LENGTH_SHORT).show()
                    } else {
                        state.image.value = compressedImage

                        Toast.makeText(context, "Image added", Toast.LENGTH_SHORT).show()
                    }
                }
            }




    }
    var showDialog by remember { mutableStateOf(true) }

    Scaffold(topBar = {
        TopAppBar(title = { "Add Contact Screen" })
    }) {

        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                properties = DialogProperties()
            ) {


                Card(
                    modifier = Modifier
                        .width(500.dp)
                        .height(500.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.elevatedCardElevation(10.dp)
                )
                {

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),

                        ) {

                        Text(
                            text = "Add Contact",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(bottom = 4.dp, start = 80.dp)
                        )



                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.image),
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    launcher.launch("image/*")
                                }
                                    .padding(top = 7.dp)
                                    .size(100.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop

                            )
//                            Spacer(modifier = Modifier.height(6.dp))

                            OutlinedTextField(
                                value =
                                state.name.value,
                                onValueChange = {
                                    state.name.value = it
                                },
                                label = {
                                    Text(text = "Enter Your Name")
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .width(330.dp)

                            )


//                            Spacer(modifier = Modifier.height(13.dp))

                            OutlinedTextField(
                                value = state.number.value,
                                onValueChange = { state.number.value = it },

                                label = {
                                    Text(text = "Enter Your Mobile")
                                },
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .width(330.dp)


                            )



//                            Spacer(modifier = Modifier.height(13.dp))
                            OutlinedTextField(
                                value = state.email.value,
                                onValueChange = { state.email.value = it },
                                label = {
                                    Text(text = "Enter Your Email")
                                },

                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .width(330.dp)


                            )

//                            Spacer(modifier = Modifier.height(19.dp))

                            Button(
                                onClick = {
                                    onEvent.invoke()
                                    navController.navigateUp()
                                },
                                modifier = Modifier
                                    .width(400.dp)
                                    .height(45.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                                shape = RoundedCornerShape(12.dp),


                                ) {

                                Text(text = "Save Contact")
                            }

                        }
                    }


                }



            }

        }
    }
}

fun compressImage( imageData: ByteArray ): ByteArray {

    val bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.size)
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream) // Adjust quality as needed
    return outputStream.toByteArray()
}