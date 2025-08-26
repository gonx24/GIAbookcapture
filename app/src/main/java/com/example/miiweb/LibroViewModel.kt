package com.example.miiweb

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.miiweb.data.Libro
import com.example.miiweb.data.LibroDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import java.io.IOException

class LibroViewModel(private val libroDao: LibroDao) : ViewModel(){

    private val _libros = MutableStateFlow<List<Libro>>(emptyList()) // Usa un StateFlow para la lista de libros
    val todosLosLibros: StateFlow<List<Libro>> = _libros.asStateFlow()

    private val _librosEliminados = MutableSharedFlow<Unit>()
    val librosEliminados = _librosEliminados.asSharedFlow()

    init {
        viewModelScope.launch {
            cargarLibros() // Carga los libros al iniciar el ViewModel
        }
    }

    fun exportarLibrosACSV(context: Context) {
        viewModelScope.launch(Dispatchers.IO) { // Usa Dispatchers.IO para operaciones de archivo
            val libros = libroDao.obtenerTodosLosLibros()
            val archivoCSV = generarArchivoCSV(context, libros)
            // Comparte el archivo usando FileProvider

            val uri = FileProvider.getUriForFile(
                context,
        /*        "${context.packageName}.fileprovider", // Reemplaza con tu autoridad de FileProvider*/
                "${context.applicationContext.packageName}.fileprovider", // Corrección aquí

                archivoCSV
            )

            val intentCompartir = Intent(Intent.ACTION_SEND).apply {
                type = "text/csv"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }



            Log.d("LibroViewModel", "Iniciando exportación a CSV") // Log al inicio de la función
            Log.d("LibroViewModel", "Libros obtenidos: ${libros.size}") // Log después de obtener los libros
            Log.d("LibroViewModel", "Archivo CSV generado: ${archivoCSV.absolutePath}") // Log después de generar el archivo

            // Inicia la actividad de compartir
            context.startActivity(Intent.createChooser(intentCompartir, "Compartir CSV"))
        }
            // Puedes mostrar una notificación o abrir el archivo aquí

    }

    private fun generarArchivoCSV(context: Context, libros: List<Libro>): File {
        val nombreArchivo = "libros.csv"
        val archivo = File(context.filesDir, nombreArchivo)

        try {
            archivo.printWriter().use { out ->// Escribe la cabecera del CSV
                out.println("Titulo,Autor,Editor,FechaPublicacion,Edicion,ISBN,Categoria,Descripcion")

                // Escribe los datos de cada libro
                libros.forEach { libro ->
                    out.println("${libro.titulo},${libro.autor},${libro.editor},${libro.publicacion},${libro.edicion},${libro.isbn},${libro.categoria},${libro.descripcion}")
                }
            }
        } catch (e: IOException) {
            Log.e("LibroViewModel", "Error al exportar aCSV", e)
        }

        return archivo
    }
    fun cargarLibros() {
        viewModelScope.launch {
            _libros.value = libroDao.obtenerTodosLosLibros() // Actualiza el StateFlow con los libros de la base de datos
        }
    }

    fun eliminarLibros(librosIds: List<Int>) {
        viewModelScope.launch {
            Log.d("LibroViewModel", "Eliminando libros con IDs: $librosIds")
            if (librosIds.isEmpty()) {
                Log.e("LibroViewModel", "La lista de IDs está vacía")
            } else {
                libroDao.eliminarLibros(librosIds)
                _librosEliminados.emit(Unit) // Emite un valor para notificar la eliminación
                cargarLibros() // Recarga la lista de libros después de la eliminación
            }
        }
    }
    /*
    val todosLosLibros: StateFlow<List<Libro>> = libroDao.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
*/
    fun insertarLibro(titulo: String, autor:String, editor: String, publicacion: String, edicion: String, isbn: String, categoria: String, descripcion: String) {
        viewModelScope.launch {
            val nuevoLibro = Libro(titulo = titulo, autor = autor, editor = editor, publicacion = publicacion, edicion = edicion, isbn = isbn, categoria = categoria, descripcion = descripcion)
            libroDao.insertar(nuevoLibro)
        }
    }
/*
    fun eliminarLibros(librosIds: List<Int>) {
        viewModelScope.launch {
            Log.d("LibroViewModel", "Eliminando libros con IDs: $librosIds") // Imprime los IDs recibidos
            libroDao.eliminarLibros(librosIds)

        }
    }*/

    suspend fun obtenerLibroPorId(libroId: Int): Libro? {
        return viewModelScope.async(Dispatchers.IO) { // Usa Dispatchers.IO para ejecutar en un hilo en segundo plano
            libroDao.obtenerLibroPorId(libroId)
        }.await()
    }
        fun actualizarLibro(
            libroId: Int,
            titulo: String,
            autor: String,
            editor: String,
            publicacion: String,
            edicion: String,
            isbn: String,
            categoria: String,
            descripcion: String
        ) {
            viewModelScope.launch {
                libroDao.actualizarLibro(
                    Libro(
                        id = libroId,
                        titulo = titulo,
                        autor = autor,
                        editor = editor,
                        publicacion = publicacion,
                        edicion = edicion,
                        isbn = isbn,
                        categoria = categoria,
                        descripcion = descripcion
                    )
                )
            }
        }
    }
