package com.example.rumahhewan.ui.viewmodel.perawatan

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Perawatan
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.PerawatanRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPerawatanState {
    data class Success(val perawatan: Perawatan) : DetailPerawatanState()
    data class Error(val message: String) : DetailPerawatanState()
    object Loading : DetailPerawatanState()
}

class DetailPerawatanViewModel(
    savedStateHandle: SavedStateHandle,
    private val pwt: PerawatanRepository
) : ViewModel() {
    private val _pwt: String = checkNotNull(savedStateHandle["id_perawatan"])

    private val _detailPerawatanState = MutableStateFlow<DetailPerawatanState>(DetailPerawatanState.Loading)
    val detailPerawatanState: StateFlow<DetailPerawatanState> = _detailPerawatanState.asStateFlow()

    init {
        getPerawatanByPwt(_pwt)
    }

    private fun getPerawatanByPwt(idperawatan: String) {
        viewModelScope.launch {
            _detailPerawatanState.value = DetailPerawatanState.Loading
            _detailPerawatanState.value = try {
                val perawatan = pwt.getPerawatanById(idperawatan)
                DetailPerawatanState.Success(perawatan)
            } catch (e: IOException) {
                DetailPerawatanState.Error("Terjadi kesalahan jaringan")
            } catch (e: HttpException) {
                DetailPerawatanState.Error("Terjadi kesalahan server")
            }
        }
    }
}