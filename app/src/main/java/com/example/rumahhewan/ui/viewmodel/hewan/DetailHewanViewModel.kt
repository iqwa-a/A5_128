package com.example.rumahhewan.ui.viewmodel.hewan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailHewanState {
    data class Success(val hewan: Hewan) : DetailHewanState()
    data class Error(val message: String) : DetailHewanState()
    object Loading : DetailHewanState()
}

class DetailHewanViewModel(
    savedStateHandle: SavedStateHandle,
    private val hwn: HewanRepository
) : ViewModel() {
    private val _hwn: String = checkNotNull(savedStateHandle["id_hewan"])

    private val _detailHewanState = MutableStateFlow<DetailHewanState>(DetailHewanState.Loading)
    val detailHewanState: StateFlow<DetailHewanState> = _detailHewanState.asStateFlow()

    init {
        getHewanByHwn(_hwn)
    }

    private fun getHewanByHwn(idhewan: String) {
        viewModelScope.launch {
            _detailHewanState.value = DetailHewanState.Loading
            _detailHewanState.value = try {
                val hewan = hwn.getHewanById(idhewan)
                DetailHewanState.Success(hewan)
            } catch (e: IOException) {
                DetailHewanState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailHewanState.Error("Terjadi kesalahan server")
            }
        }
    }
}