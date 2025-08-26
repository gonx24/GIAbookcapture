package com.example.miiweb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.miiweb.data.AppDatabase
import com.example.miiweb.ui.theme.MiiwebTheme
import androidx.compose.ui.platform.LocalContext

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database by lazy { Room.databaseBuilder(applicationContext, AppDatabase::class.java, "libro-db").build() }
        val libroViewModel by viewModels<LibroViewModel> { LibroViewModelFactory(database.libroDao()) }

        setContent {

            val navController = rememberNavController()


            MiiwebTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold { innerPadding ->
                        Navigation(innerPadding,navController, libroViewModel) // Pasa el viewModel a Navigation
                    }
                    Scaffold(
                       topBar = { MiBarraDeAplicacion(navController,libroViewModel, LocalContext.current) }

                    ) { innerPadding ->
                        Box(modifier = Modifier
                            .padding(innerPadding).fillMaxSize()
                        ) {
                            // Contenido
                           Navigation(innerPadding,navController,libroViewModel)

                        }
                    }
                }
            }
        }
    }
}