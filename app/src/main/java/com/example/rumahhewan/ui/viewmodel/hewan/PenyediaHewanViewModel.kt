package com.example.rumahhewan.ui.viewmodel.hewan

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rumahhewan.HewanApplications

object PenyediaHewanViewModel {
    val Factory = viewModelFactory {
        initializer { HomeHewanViewModel(aplikasiHewan().container.hewanRepository) }
        initializer { InsertHewanViewModel(aplikasiHewan().container.hewanRepository) }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailHewanViewModel(savedStateHandle, aplikasiHewan().container.hewanRepository)
        }
        initializer { UpdateHewanViewModel(aplikasiHewan().container.hewanRepository) }
    }
}

fun CreationExtras.aplikasiHewan(): HewanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as HewanApplications)