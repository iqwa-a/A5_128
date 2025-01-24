package com.example.rumahhewan.ui.viewmodel.dokter

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.DokterRepository
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailDokterState {
    data class Success(val dokter: Dokter) : DetailDokterState()
    data class Error(val message: String) : DetailDokterState()
    object Loading : DetailDokterState()
}

class DetailDokterViewModel(
    savedStateHandle: SavedStateHandle,
    private val dkr: DokterRepository
) : ViewModel() {
    private val _dkr: String = checkNotNull(savedStateHandle["id_dokter"])

    private val _detailDokterState = MutableStateFlow<DetailDokterState>(DetailDokterState.Loading)
    val detailDokterState: StateFlow<DetailDokterState> = _detailDokterState.asStateFlow()

    init {
        getDokterById(_dkr)
    }

    private fun getDokterById(iddokter: String) {
        viewModelScope.launch {
            _detailDokterState.value = DetailDokterState.Loading
            _detailDokterState.value = try {
                val dokter = dkr.getDokterById(iddokter)
                DetailDokterState.Success(dokter)
            } catch (e: IOException) {
                DetailDokterState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailDokterState.Error("Terjadi kesalahan server")
            }
        }
    }
}