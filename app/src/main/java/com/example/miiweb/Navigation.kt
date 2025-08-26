package com.example.miiweb

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument

@Composable
fun Navigation(innerPadding: PaddingValues, navController: NavHostController, libroViewModel: LibroViewModel) {


   NavHost(
        navController = navController,
        startDestination = Screen.ListaScreen.route
    ) {
        composable(route =  Screen.BakingScreen.route) { BakingScreen(navController) }
        composable(Screen.ListaScreen.route) {  ListaScreen(navController, libroViewModel) }
        composable(Screen.AboutScreen.route) {  AboutScreen() }

       composable(
           route = Screen.EditLibroScreen.route,
           arguments = listOf(navArgument("result") { type = NavType.StringType })
       ) { backStackEntry ->
           val result = backStackEntry.arguments?.getString("result")
           DatosLibro(navController,result,libroViewModel)
       }

       composable(
           route = "editar_libro/{libroId}",
           arguments = listOf(navArgument("libroId") { type = NavType.IntType })
       ) {backStackEntry ->
           val libroId = backStackEntry.arguments?.getInt("libroId") ?: 0
           PantallaEditarLibro(navController, libroId, libroViewModel) // Pasa el objeto libroViewModel directamente

       }
   }
}