package com.example.rumahhewan.ui.viewmodel.jenishewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.JenisHewanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailJenisState {
    data class Success(val jenis: Jenis) : DetailJenisState()
    data class Error(val message: String) : DetailJenisState()
    object Loading : DetailJenisState()
}

class DetailJenisViewModel(
    savedStateHandle: SavedStateHandle,
    private val jns: JenisHewanRepository
) : ViewModel() {
    private val _idJenis: String = checkNotNull(savedStateHandle["id_jenis_hewan"])

    private val _detailJenisState = MutableStateFlow<DetailJenisState>(DetailJenisState.Loading)
    val detailJenisState: StateFlow<DetailJenisState> = _detailJenisState.asStateFlow()

    init {
        getJenisById(_idJenis)
    }

     fun getJenisById(idJenis: String) {
        viewModelScope.launch {
            _detailJenisState.value = DetailJenisState.Loading
            _detailJenisState.value = try {
                val jenis = jns.getJenisById(idJenis)
                DetailJenisState.Success(jenis)
            } catch (e: IOException) {
                DetailJenisState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailJenisState.Error("Terjadi kesalahan server")
            }
        }
    }
}