package com.example.rumahhewan.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.launch

class UpdateHewanViewModel(
    private val hwn: HewanRepository
) : ViewModel() {

    var hewanState by mutableStateOf(UpdateHewanState())
        private set

    fun updateUpdateHwnState(updateHewanEvent: UpdateHewanEvent) {
        hewanState = hewanState.copy(updateHewanEvent = updateHewanEvent)
    }

    fun getHewanByIdhewan(idhewan: String) {
        viewModelScope.launch {
            try {
                val hewan = hwn.getHewanById(idhewan)
                hewanState = UpdateHewanState(updateHewanEvent = hewan.toUpdateHewanEvent())
            } catch (e: Exception) {
                e.printStackTrace()
                hewanState = UpdateHewanState(isError = true, errorMessage = e.message)
            }
        }
    }

    fun updateHwn() {
        viewModelScope.launch {
            try {
                val hewan = hewanState.updateHewanEvent.toHwn()
                hwn.updateHewan(hewan.idhewan, hewan)
                hewanState = hewanState.copy(isSuccess = true)
            } catch (e: Exception) {
                e.printStackTrace()
                hewanState = hewanState.copy(isError = true, errorMessage = e.message)
            }
        }
    }
}
data class UpdateHewanState(
    val updateHewanEvent: UpdateHewanEvent = UpdateHewanEvent(),
    val isSuccess: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
)

data class UpdateHewanEvent(
    val idhewan: String = "",
    val namahewan: String = "",
    val jenishewanid: String = "",
    val pemilik: String = "",
    val kontakpemilik: String = "",
    val tanggallahir: String = "",
    val catatankesehatan: String = ""
)
fun UpdateHewanEvent.toHwn(): Hewan = Hewan(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    pemilik = pemilik,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    catatankesehatan = catatankesehatan
)

fun Hewan.toUpdateHewanEvent(): UpdateHewanEvent = UpdateHewanEvent(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    pemilik = pemilik,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    catatankesehatan = catatankesehatan
)