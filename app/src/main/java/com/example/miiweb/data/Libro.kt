package com.example.miiweb.data


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Libro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo:String,
    val autor: String,
    val editor: String,
    val publicacion: String,
    val edicion: String,
    val isbn: String,
    val categoria: String,
    val descripcion: String,
    // ... otros campos del libro
)