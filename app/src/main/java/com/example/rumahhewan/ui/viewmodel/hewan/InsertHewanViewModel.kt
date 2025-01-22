package com.example.rumahhewan.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.launch

class InsertHewanViewModel (private val hwn: HewanRepository): ViewModel(){
    var hewanState by mutableStateOf(InsertHewanState())
        private set

    fun updateInsertHwnState(insertHewanEvent: InsertHewanEvent){
        hewanState = InsertHewanState(insertHewanEvent = insertHewanEvent)
    }

    suspend fun insertHwn(){
        viewModelScope.launch {
            try {
                hwn.insertHewan(hewanState.insertHewanEvent.toHwn())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertHewanState(
    val insertHewanEvent : InsertHewanEvent = InsertHewanEvent()
)

data class InsertHewanEvent(
    val idhewan: String = "",
    val namahewan: String = "",
    val jenishewanid: String = "",
    val pemilik: String = "",
    val kontakpemilik: String = "",
    val tanggallahir: String = "",
    val catatankesehatan: String = ""

)

fun InsertHewanEvent.toHwn(): Hewan = Hewan(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    pemilik = pemilik,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    catatankesehatan = catatankesehatan

)

fun Hewan.toHewanStateHwn():InsertHewanState = InsertHewanState(
    insertHewanEvent = toInsertHewanEvent()
)

fun Hewan.toInsertHewanEvent():InsertHewanEvent = InsertHewanEvent(
    idhewan = idhewan,
    namahewan = namahewan,
    jenishewanid = jenishewanid,
    pemilik = pemilik,
    kontakpemilik = kontakpemilik,
    tanggallahir = tanggallahir,
    catatankesehatan = catatankesehatan
)