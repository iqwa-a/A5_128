package com.example.rumahhewan.ui.viewmodel.jenishewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.JenisHewanRepository
import kotlinx.coroutines.launch

class UpdateJenisHewanViewModel(
    private val jns: JenisHewanRepository
) : ViewModel() {

    var jenisState by mutableStateOf(UpdateJenisState())
        private set

    /**
     * Update state with a new UpdateJenisEvent.
     */
    fun updateUpdateJnsState(updateJenisEvent: UpdateJenisEvent) {
        jenisState = jenisState.copy(updateJenisEvent = updateJenisEvent)
    }

    /**
     * Fetch a specific Jenis by ID.
     */
    fun getJenisById(idjenishewan: String) {
        viewModelScope.launch {
            try {
                val jenis = jns.getJenisById(idjenishewan)
                jenisState = jenisState.copy(
                    updateJenisEvent = jenis.toUpdateJenisEvent(),
                    isError = false, // Clear any previous error
                    errorMessage = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
                jenisState = jenisState.copy(
                    isError = true,
                    errorMessage = e.message
                )
            }
        }
    }

    /**
     * Update a specific Jenis using the current state.
     */
    fun updateJns() {
        viewModelScope.launch {
            try {
                val jenis = jenisState.updateJenisEvent.toJns()
                jns.updateJenis(jenis.idjenishewan, jenis)
                jenisState = jenisState.copy(
                    isSuccess = true,
                    isError = false, // Clear any previous error
                    errorMessage = null
                )
            } catch (e: Exception) {
                e.printStackTrace()
                jenisState = jenisState.copy(
                    isError = true,
                    errorMessage = e.message,
                    isSuccess = false // Reset success flag on failure
                )
            }
        }
    }
}

data class UpdateJenisState(
    val updateJenisEvent: UpdateJenisEvent = UpdateJenisEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)


data class UpdateJenisEvent(
    val idjenishewan: String = "",
    val namajenishewan: String = "",
    val deskripsi: String = ""
)

fun UpdateJenisEvent.toJns(): Jenis = Jenis(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)

fun Jenis.toUpdateJenisEvent(): UpdateJenisEvent = UpdateJenisEvent(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi
)