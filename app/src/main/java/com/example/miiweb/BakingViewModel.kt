package com.example.miiweb

import android.app.Application
import android.graphics.Bitmap
import android.util.Log
//import androidx.compose.ui.window.application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.miiweb.data.AppDatabase
import com.example.miiweb.data.Libro
import com.example.miiweb.data.LibroDao
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch



class BakingViewModel (application: Application) : AndroidViewModel(application) {

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    fun sendPrompt(
        bitmap: Bitmap,
        prompt: String
    ) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        image(bitmap)
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }
/*
    private val libroDao: LibroDao

    init {
        val database = Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "mi_base_de_datos"
        ).build()
        libroDao = database.libroDao()
    }*/
   /* fun guardarLibro(titulo: String, autor: String, editor: String, publicacion:String, edicion:String, isbn:String, categoria:String, descripcion:String) {
    viewModelScope.launch {val nuevoLibro = Libro(titulo = titulo, autor = autor, editor = editor, publicacion = publicacion, edicion = edicion, isbn = isbn, categoria = categoria, descripcion = descripcion)
            // Asegúrate de tener una referencia a libroDao en tu ViewModel
            libroDao.insertar(nuevoLibro)
            Log.d("MiEtiqueta", "Libro guardado en la base de datos")
        }
    }*/
}

