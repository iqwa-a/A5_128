package com.example.rumahhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.DokterRepository
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.launch

class UpdateDokterViewModel(
    private val dkr: DokterRepository
) : ViewModel() {

    var dokterState by mutableStateOf(UpdateDokterState())
        private set

    fun updateUpdateDkrState(updateDokterEvent: UpdateDokterEvent) {
        dokterState = dokterState.copy(updateDokterEvent = updateDokterEvent)
    }

    fun getDokterByIddokter(iddokter: String) {
        viewModelScope.launch {
            try {
                val dokter = dkr.getDokterById(iddokter)
                dokterState = UpdateDokterState(updateDokterEvent = dokter.toUpdateDokterEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                dokterState = UpdateDokterState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updatedkr() {
        viewModelScope.launch {
            try {
                val dokter = dokterState.updateDokterEvent.toDkr()
                dkr.updateDokter(dokter.iddokter, dokter)
                dokterState = dokterState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                dokterState = dokterState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateDokterState(
    val updateDokterEvent: UpdateDokterEvent = UpdateDokterEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateDokterEvent(
    val iddokter: String = "",
    val namadokter: String = "",
    val spesialisasi: String = "",
    val kontak: String = "",
)
fun UpdateDokterEvent.toDkr(): Dokter = Dokter(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)

fun Dokter.toUpdateDokterEvent(): UpdateDokterEvent = UpdateDokterEvent(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)