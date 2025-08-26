package com.example.miiweb

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miiweb.data.LibroDao

class LibroViewModelFactory(private val libroDao: LibroDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LibroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LibroViewModel(libroDao) as T
        } ;throw IllegalArgumentException("Unknown ViewModel class")
    }
}