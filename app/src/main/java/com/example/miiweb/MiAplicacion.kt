package com.example.miiweb

import android.app.Application
import androidx.room.Room
import com.example.miiweb.data.AppDatabase // Reemplaza con tu paquete

class MiAplicacion :Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mi_base_de_datos" // Nombre de la base de datos
        ).build()
    }
}
