package com.example.miiweb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface LibroDao {
    @Insert
    suspend fun insertar(libro: Libro)

    @Query("SELECT * FROM Libro")
    fun obtenerTodos(): Flow<List<Libro>>

    @Query("SELECT * FROM libro WHERE id = :libroId")
    fun obtenerLibroPorId(libroId: Int): Libro?
    // ... otros métodos que necesites

    @Query("DELETE FROM libro WHERE id IN (:libroIds)")
    suspend fun eliminarLibros(libroIds: List<Int>)

    @Query("SELECT * FROM libro")
    suspend fun obtenerTodosLosLibros(): List<Libro>
    @Update
    suspend fun actualizarLibro(libro: Libro)
}