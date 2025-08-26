package com.example.miiweb

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MiBarraDeAplicacion(navController: NavHostController,viewModel: LibroViewModel,context: Context) {


        var showMenu by remember { mutableStateOf(false)}

        TopAppBar(

        title = { Text("Gemini AI Book Capture") },
        colors = TopAppBarDefaults.smallTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        actions = {
            IconButton(onClick = { showMenu = !showMenu }) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }

            DropdownMenu(
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            ) {

                DropdownMenuItem(
                    text = { Text("Capturar Libro") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.AddCircle, // Icono directamente de la biblioteca
                            contentDescription = "Capturar Libro"
                        )},
                    onClick = {

                        navController.navigate(route = "baking_screen")  /* Acción al seleccionar Configuración */ }
                )

                DropdownMenuItem(
                    text = { Text("Ver Libros") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search, // Icono directamente de la biblioteca
                            contentDescription = "lista_screen"
                        )},

                    onClick = { navController.navigate("lista_screen")/* Acción al seleccionar Configuración */
                    }


                )
                DropdownMenuItem(
                    text = { Text("Exportar CVS") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Share, // Icono directamente de la biblioteca
                            contentDescription = "Exportar CVS"
                        )},
                    onClick = {  viewModel.exportarLibrosACSV(context)  }
                )
               /* DropdownMenuItem(
                    text = { Text("Configuración") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Build, // Icono directamente de la biblioteca
                            contentDescription = "Configuración"
                        )},
                    onClick = { /* Acción al seleccionar Configuración */ }
                )*/
                DropdownMenuItem(
                    text = { Text("Acerca de...") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Info, // Icono directamente de la biblioteca
                            contentDescription = "Acerca de..."
                        )},
                    onClick = { navController.navigate("about_screen")/* Acción al seleccionar Ayuda */ }
                )
            }
        },)
}



