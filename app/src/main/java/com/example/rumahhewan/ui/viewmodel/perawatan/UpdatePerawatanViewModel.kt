package com.example.rumahhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Perawatan
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.PerawatanRepository
import kotlinx.coroutines.launch

class UpdatePerawatanViewModel(
    private val pwt: PerawatanRepository
) : ViewModel() {

    var perawatanState by mutableStateOf(UpdatePerawatanState())
        private set

    fun updateUpdatePwtState(updatePerawatanEvent: UpdatePerawatanEvent) {
        perawatanState = perawatanState.copy(updatePerawatanEvent = updatePerawatanEvent)
    }

    fun getPerawatanByIdperawatan(idperawatan: String) {
        viewModelScope.launch {
            try {
                val perawatan = pwt.getPerawatanById(idperawatan)
                perawatanState = UpdatePerawatanState(updatePerawatanEvent = perawatan.toUpdatePerawatanEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                perawatanState = UpdatePerawatanState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updatePwt() {
        viewModelScope.launch {
            try {
                val perawatan = perawatanState.updatePerawatanEvent.toPwt()
                pwt.updatePerawatan(perawatan.idperawatan, perawatan)
                perawatanState = perawatanState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                perawatanState = perawatanState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdatePerawatanState(
    val updatePerawatanEvent: UpdatePerawatanEvent = UpdatePerawatanEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdatePerawatanEvent(
    val idperawatan: String = "",
    val idhewan: String = "",
    val iddokter: String = "",
    val tanggalperawatan: String = "",
    val detailperawatan: String = ""
)

fun UpdatePerawatanEvent.toPwt(): Perawatan = Perawatan(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)

fun Perawatan.toUpdatePerawatanEvent(): UpdatePerawatanEvent = UpdatePerawatanEvent(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan
)