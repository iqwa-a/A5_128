package com.example.rumahhewan.ui.viewmodel.jenishewan

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.rumahhewan.HewanApplications
import com.example.rumahhewan.ui.viewmodel.hewan.UpdateHewanViewModel

object PenyediaJenisHewanViewModel {
    val Factory = viewModelFactory {
        //initializer { HomeJenisHewanViewModel(aplikasiJenisHewan().container.JenishewanRepository) }
        initializer { InsertJenisHewanViewModel(aplikasiHewan().container.jenishewanRepository) }
       initializer {
            val savedStateHandle = createSavedStateHandle()
         DetailJenisHewanViewModel(savedStateHandle, aplikasiJenisHewan().container.jenisRepository)
        }
        initializer { UpdateHewanViewModel(aplikasiHewan().container.hewanRepository) }

    }
}

fun CreationExtras.aplikasiHewan(): HewanApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]as HewanApplications)