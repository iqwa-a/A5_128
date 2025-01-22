package com.example.rumahhewan.ui.viewmodel.jenishewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.JenisHewanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeJenisState {
    data class Success(val jenis: List<Jenis>) : HomeJenisState()
    object Error : HomeJenisState()
    object Loading : HomeJenisState()
}

class HomeJenisViewModel(private val jns: JenisHewanRepository) : ViewModel() {
    var jenisState: HomeJenisState by mutableStateOf(HomeJenisState.Loading)
        private set

    init {
        getJns()
    }

    fun getJns(){
        viewModelScope.launch {
            jenisState = HomeJenisState.Loading
            jenisState = try {
                HomeJenisState.Success(jns.getJenis())  // Mengambil daftar jenis hewan
            } catch (e: IOException) {
                HomeJenisState.Error
            } catch (e: HttpException) {
                HomeJenisState.Error
            }
        }
    }

    fun deleteJns(idjenishewan: String) {
        viewModelScope.launch {
            try {
                jns.deleteJenis(idjenishewan)  // Menghapus jenis hewan berdasarkan ID
            } catch (e: IOException) {
                jenisState = HomeJenisState.Error
            } catch (e: HttpException) {
                jenisState = HomeJenisState.Error
            }
        }
    }
}