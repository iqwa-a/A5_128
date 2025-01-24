package com.example.rumahhewan.ui.viewmodel.dokter

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rumahhewan.HewanApplications


object PenyediaDokterViewModel {
    val Factory = viewModelFactory {
        initializer { HomeDokterViewModel(aplikasiHewan().container.dokterRepository) }
        initializer { InsertDokterViewModel(aplikasiHewan().container.dokterRepository) }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailDokterViewModel(savedStateHandle, aplikasiHewan().container.dokterRepository)
        }
        initializer { UpdateDokterViewModel(aplikasiHewan().container.dokterRepository) }
    }
}

fun CreationExtras.aplikasiHewan(): HewanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as HewanApplications)