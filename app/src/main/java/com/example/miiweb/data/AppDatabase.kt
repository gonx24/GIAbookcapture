package com.example.miiweb.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Libro::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
}