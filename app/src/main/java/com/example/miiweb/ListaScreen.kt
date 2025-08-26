package com.example.miiweb

import android.util.Log
import androidx.activity.result.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import java.io.File
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun ListaScreen(navController: NavHostController, viewModel: LibroViewModel) {

    val libros by viewModel.todosLosLibros.collectAsState() // Observa el StateFlow de libros
    val selectedItems = remember { mutableStateListOf<Int>() }
    val coroutineScope = rememberCoroutineScope()
    var allItemsSelected by remember { mutableStateOf(false) }


   /* val libros by viewModel.todosLosLibros.collectAsState()*/
/*    val selectedItems = remember { mutableStateListOf<Int>() } // Lista de IDs de libros seleccionados*/

    if (libros.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No posee libros cargados")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                // Navega a la pantalla para capturar un nuevo libro
                navController.navigate(route = "baking_screen") // Reemplaza "capturar_libro" con la ruta correcta
            }) {
                Text("Capturar Libro")
            }
        }
    } else {
      /*  LazyColumn(modifier = Modifier.padding(16.dp)) {
            items(libros) { libro ->
                Button(onClick = { navController.navigate("editar_libro/${libro.id}") }) {
                    Text(text = "${libro.titulo} by ${libro.autor} ${libro.id} ")
                }
            }
        }*/

        LazyColumn(modifier = Modifier.padding(16.dp)
            .padding(top = 80.dp) // Agrega padding inferior
        ) {
            items(libros) { libro ->
                val isSelected = libro.id in selectedItems

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)

                        .clickable {
                            navController.navigate("editar_libro/${libro.id}")
                          /*  if (isSelected) {
                                selectedItems.remove(libro.id)
                                Log.d("ListaScreen", "Elemento deseleccionado: ${libro.id}")
                            } else {
                                selectedItems.add(libro.id) // Asegúrate de que esta línea se esté ejecutando correctamente
                                Log.d("ListaScreen", "Elemento seleccionado: ${libro.id}")
                            }*/
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) {
                            MaterialTheme.colorScheme.surfaceVariant // Color de fondo para elementos seleccionados
                        } else {
                            MaterialTheme.colorScheme.surface // Color de fondo por defecto
                        }

                    )

                ) {
                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "${libro.titulo} by ${libro.autor}")
                            Text(text = "ID: ${libro.id}")


                        }
                        // Agrega un Checkbox para seleccionar/deseleccionar
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = { isChecked -> // Agrega un parámetro 'isChecked'
                                if (isChecked) {
                                    selectedItems.add(libro.id)
                                    Log.d("ListaScreen", "Elemento seleccionado (Checkbox): ${libro.id}")
                                } else {
                                    selectedItems.remove(libro.id)
                                    Log.d("ListaScreen", "Elemento deseleccionado (Checkbox): ${libro.id}")
                                }
                                allItemsSelected = selectedItems.size == libros.size // Actualiza el estado del botón "Seleccionar Todos"
                            }
                        )

                    }

                }

            }

        }

    }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center// Distribuye los botones con espacio alrededor
    ) {
        val context = LocalContext.current // Obtiene el contexto de aplicación


        Button(

            onClick = {navController.navigate(Screen.BakingScreen.route)  },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Capturar")
        }

        Button(onClick = {
            allItemsSelected = !allItemsSelected
            if (allItemsSelected) {
                selectedItems.addAll(libros.map { it.id })
            } else {
                selectedItems.clear()
            }
        },
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = if (allItemsSelected) Icons.Filled.ArrowDropDown else Icons.Filled.List,
                contentDescription = if (allItemsSelected) "Deseleccionar Todos" else "Seleccionar Todos"
            )
            /*Text(text = if (allItemsSelected) "Deseleccionar Todos" else "Seleccionar Todos")*/
        }

        Button(
            onClick = {
                Log.d("ListaScreen", "IDs seleccionados: $selectedItems")
                coroutineScope.launch {
                    viewModel.eliminarLibros(selectedItems)
                    /*   selectedItems.clear()*/
                }
            },
            modifier = Modifier.padding(16.dp).padding(bottom = 16.dp) // Agrega margen superior
            ,

            enabled = selectedItems.isNotEmpty() // Habilita el botón solo si hay items seleccionados
        ) {
            Icon(
                imageVector = Icons.Filled.Delete ,
                contentDescription = "Eliminar Seleccionados"
            )
            /*Text("Eliminar")*/

        }

        LaunchedEffect(Unit) {
            viewModel.librosEliminados.collect {
                selectedItems.clear() // Limpia la lista después de que se emita el evento de eliminación

                // No necesitas hacer nada aquí, ya que la lista se recarga en el ViewModel
            }
        }
    }
}