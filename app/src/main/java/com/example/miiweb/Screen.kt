package com.example.miiweb

sealed class Screen(val route: String) {
    object AboutScreen : Screen("about_screen")
    object BakingScreen : Screen("baking_screen")
    object ListaScreen : Screen("lista_screen")
    object EditLibroScreen : Screen("edit_libro_screen/{result}") { // Agrega el argumento "result"
        fun createRoute(result: String) = "edit_libro_screen/$result" // Función para crear la ruta con el argumento
    }
}