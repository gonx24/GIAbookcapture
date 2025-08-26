package com.example.miiweb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.miiweb.DatosLibro
import androidx.compose.foundation.lazy.LazyColumn


@Composable
fun BakingScreen(navController: NavHostController,
                 bakingViewModel: BakingViewModel = viewModel()
) {

    val placeholderPrompt = stringResource(R.string.prompt_placeholder)
    val placeholderResult = stringResource(R.string.results_placeholder)
    var prompt by rememberSaveable { mutableStateOf(placeholderPrompt) }
    var result by rememberSaveable { mutableStateOf(placeholderResult) }
    val uiState by bakingViewModel.uiState.collectAsState()
//    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    val capturedImages = remember { mutableStateListOf<Bitmap?>(null, null) }
    var currentImageIndex by remember{ mutableStateOf(0) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        capturedImages[currentImageIndex] = bitmap
        currentImageIndex = (currentImageIndex + 1) % capturedImages.size // Alterna entre 0 y 1
    }

    //val context = LocalContext.current // Obtén el contexto aquí

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        val listState = rememberLazyListState() // Use LazyListState


        LazyColumn(
            modifier = Modifier.run {
                fillMaxSize()
                    .padding(16.dp)

            }, // Agrega el modificador verticalScroll
            state = listState
        ) {
            item {
                Text(
                    text = stringResource(R.string.baking_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = stringResource(R.string.capture_desc),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                Button(
                    onClick = {
                        lifecycleOwner.lifecycleScope.launchWhenStarted {
                            launcher.launch()
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Abrir Cámara")
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    capturedImages.forEach { bitmap ->
                        bitmap?.let {
                            Image(
                                bitmap = it.asImageBitmap(),
                                contentDescription = "Imagen capturada",
                                modifier = Modifier
                                    .weight(1f) // Divide el espacio disponible equitativamente
                                    .aspectRatio(1f)
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.padding(all = 16.dp)
                ) {
                    Button(
                        onClick = {
                            // Combina las imágenes y envíalas al ViewModel
                            if (capturedImages[0] != null && capturedImages[1] != null) {
                                val combinedBitmap = combineBitmaps(capturedImages[0]!!, capturedImages[1]!!)
                                bakingViewModel.sendPrompt(combinedBitmap, prompt)
                            }
                        },
                        enabled = prompt.isNotEmpty() && capturedImages.all { it != null }, // Habilita el botón solo si hay una imagen y un prompt*/
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(text = stringResource(R.string.action_go))
                    }


                }



        if (uiState is UiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            var textColor = MaterialTheme.colorScheme.onSurface
            if (uiState is UiState.Error) {
                textColor = MaterialTheme.colorScheme.error
                result = (uiState as UiState.Error).errorMessage
            } else if (uiState is UiState.Success) {
                textColor = MaterialTheme.colorScheme.onSurface
                result = (uiState as UiState.Success).outputText

                navController.navigate(Screen.EditLibroScreen.createRoute(result))
                }
             /*   DatosLibro(result)*/
         //   val scrollState = rememberScrollState()
        }}
        }
        }

    SnackbarHost(hostState = snackbarHostState)
}
fun combineBitmaps(bitmap1: Bitmap, bitmap2: Bitmap): Bitmap {
    val resultBitmap = Bitmap.createBitmap(bitmap1.width, bitmap1.height * 2, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(resultBitmap)
    canvas.drawBitmap(bitmap1, 0f, 0f, null)
    canvas.drawBitmap(bitmap2, 0f, bitmap1.height.toFloat(), null)
    return resultBitmap
}

fun SaveResult(context: Context, result: String) {
    val filename = "result.txt"
    try {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { outputStream ->
            outputStream.write(result.toByteArray())
            outputStream.write("\n".toByteArray())
        }
    } catch (e: Exception) {
        Log.e("BakingScreen", "Error al guardar el resultado en el archivo", e)
    }
}

