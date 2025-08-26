package com.example.miiweb
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue // Importación correcta
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue // Importación correcta
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.miiweb.LibroViewModel // Asegúrate de que la ruta sea correcta
import kotlinx.coroutines.launch
import kotlin.math.min


// Nueva función @Composable para mostrar el Snackbar

@Composable
fun DatosLibro(navController: NavHostController,
               result: String?,
               viewModel: LibroViewModel,

) {




    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()

        val listState = rememberLazyListState() // Use LazyListState
        val lineas = result?.split("\n") ?: emptyList() // Divide el texto en líneas usandoel salto de línea
        val valoresTextField = remember {mutableStateListOf<String>("", "", "", "", "", "", "", "") } // Inicializa con 8 valores vacíos

        var showDialog by remember { mutableStateOf(false) }

        // Actualiza valoresTextField cuando se carga la pantalla
        LaunchedEffect(result){
            if (result != null) {
                for (i in 0 until min(lineas.size, valoresTextField.size)) {
                    valoresTextField[i] = lineas[i]
                }
            }
        }
        LazyColumn(
            modifier = Modifier.run {
                fillMaxSize()
                    .padding(16.dp)

            }, // Agrega el modificador verticalScroll

            state = listState
        ) {

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(0) ?: "") } // Título
                TextField(
                    value = text,
                    onValueChange = { text = it
                                      valoresTextField[0] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Título:") },
                    placeholder = { Text("Título") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Add padding to the bottom

                )
            }
            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(1) ?: "") } // Autor
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[1] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Autor(es):") },
                    placeholder = { Text("Autor(es)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Add padding to the bottom

                )
            }

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(2) ?: "") } // Editor
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[2] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Editor:") },
                    placeholder = { Text("Editor") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp) // Add padding to the bottom

                )
            }

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(3) ?: "") } // Publicacion
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[3] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Fecha de publicación:") },
                    placeholder = { Text("Fecha de publicación") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(4) ?: "") } // Edicion
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[4] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Edición:") },
                    placeholder = { Text("Edición") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(5) ?: "") } // ISBN
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[5] = it // Actualiza el valor en la lista
                         },
                    label = { Text("ISBN:") },
                    placeholder = { Text("ISBN") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }

            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(6) ?: "") } // Categoria
                TextField(
                    value = text,
                    onValueChange = { text = it
                                      valoresTextField[6] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Categoria:") },
                    placeholder = { Text("Categoria") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }


            item {
                var text by rememberSaveable { mutableStateOf(lineas.getOrNull(7) ?: "") } // Descripcion
                TextField(
                    value = text,
                    onValueChange = {
                        text = it
                        valoresTextField[7] = it // Actualiza el valor en la lista
                                    },
                    label = { Text("Descripción:") },
                    placeholder = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )
            }

            item {
                 // Inicializa el ViewModel        val viewModel: BakingViewModel by viewModel()

                Row(
                    modifier = Modifier.fillMaxWidth(), // Ocupa todo el ancho disponible
                    horizontalArrangement = Arrangement.SpaceAround // Distribuye los botones con espacio alrededor
                ) {
                    Button(
                        onClick = {

                            coroutineScope.launch {
                               viewModel.cargarLibros()
                                /*   selectedItems.clear()*/
                            }

                            viewModel.insertarLibro(
                                valoresTextField[0],
                                valoresTextField[1],
                                valoresTextField[2],
                                valoresTextField[3],
                                valoresTextField[4],
                                valoresTextField[5],
                                valoresTextField[6],
                                valoresTextField[7]
                            )
                            navController.navigate("lista_screen")
                            showDialog = true

                        }
                        // ... (resto del código del botón)
                    ) {
                        Text(text = stringResource(R.string.action_guardar))
// ...
                    }
                    Button(onClick = {
                        navController.navigate(route = "baking_screen")  /* Acción al seleccionar Configuración */
                    }) {
                        Text("Reintentar")
                    }
                }
            }


        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Éxito") },
                text = { Text("Item guardado correctamente") },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                }
            )

        }
    }



}


