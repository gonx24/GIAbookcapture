package com.example.miiweb

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaEditarLibro(navController: NavHostController, libroId: Int, viewModel: LibroViewModel) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var titulo by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var editor by remember { mutableStateOf("") }
    var fechaPublicacion by remember { mutableStateOf("") }
    var edicion by remember { mutableStateOf("") }
    var isbn by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    // Carga la información del libro desde el ViewModel (si el ID es válido)
    LaunchedEffect(libroId) {
        if (libroId > 0) {
            val libro = viewModel.obtenerLibroPorId(libroId)
            titulo = libro?.titulo ?: ""
            autor = libro?.autor ?: ""
            editor = libro?.editor ?: ""
            fechaPublicacion = libro?.publicacion ?: ""
            edicion = libro?.edicion ?: ""
            isbn = libro?.isbn ?: ""
            categoria = libro?.categoria ?: ""
            descripcion = libro?.descripcion ?: ""
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(1) {
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text("Autor(es)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = editor,
                onValueChange = { editor = it },
                label = { Text("Editor") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fechaPublicacion,
                onValueChange = { fechaPublicacion = it },
                label = { Text("Fecha de Publicación") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = edicion,
                onValueChange = { edicion = it },
                label = { Text("Edición") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = isbn,
                onValueChange = { isbn = it },
                label = { Text("isbn") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("categoria") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp), // Agrega un margen superior de 16 dp
                horizontalArrangement = Arrangement.SpaceAround        ) {

            Button(
                onClick = {
                    coroutineScope.launch {

                        if (libroId > 0) {
                            viewModel.actualizarLibro(
                                libroId,
                                titulo,
                                autor,
                                editor,
                                fechaPublicacion,
                                edicion,
                                isbn,
                                categoria,
                                descripcion
                            )
                            viewModel.cargarLibros()


                            navController.navigate("lista_screen") // Navega de vuelta a la lista

                            /*snackbarHostState.showSnackbar("Libro actualizado")*/
                        } else {
                            viewModel.insertarLibro(
                                titulo,
                                autor,
                                editor,
                                fechaPublicacion,
                                edicion,
                                isbn,
                                categoria,
                                descripcion
                            )
                            snackbarHostState.showSnackbar("Libro guardado")

                        }
                    }

                },

            ) {
                Text(
                    text = if (libroId > 0) stringResource(id = R.string.action_actualizar) else stringResource(
                        id = R.string.action_actualizar
                    )
                )
            }

            Button(
                onClick = { navController.popBackStack() }, // Navega hacia atrás
            ) {
                Text("Regresar")
            }
        }
        }
    }
    }
