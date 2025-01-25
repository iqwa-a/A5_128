package com.example.rumahhewan.ui.viewmodel.perawatan

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rumahhewan.HewanApplications
import com.example.rumahhewan.ui.viewmodel.hewan.DetailHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.HomeHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.InsertHewanViewModel
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanViewModel

object PenyediaPerawatanViewModel {
    val Factory = viewModelFactory {
        initializer { HomePerawatanViewModel(aplikasiHewan().container.perawatanRepository) }
        initializer { InsertPerawatanViewModel(aplikasiHewan().container.perawatanRepository) }
        initializer {
            val savedStateHandle = createSavedStateHandle()
            DetailPerawatanViewModel(savedStateHandle, aplikasiHewan().container.perawatanRepository)
        }
        initializer { UpdatePerawatanViewModel(aplikasiHewan().container.perawatanRepository) }
    }
}

fun CreationExtras.aplikasiHewan(): HewanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as HewanApplications)