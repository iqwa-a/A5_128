package com.example.rumahhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.repository.DokterRepository
import kotlinx.coroutines.launch

class InsertDokterViewModel (private val dkr: DokterRepository): ViewModel(){
    var dokterState by mutableStateOf(InsertDokterState())
        private set

    fun updateInsertDkrState(insertDokterEvent: InsertDokterEvent){
        dokterState = InsertDokterState(insertDokterEvent = insertDokterEvent)
    }

    suspend fun insertDkr(){
        viewModelScope.launch {
            try {
                dkr.insertDokter(dokterState.insertDokterEvent.toDkr())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertDokterState(
    val insertDokterEvent : InsertDokterEvent = InsertDokterEvent()
)

data class InsertDokterEvent(
    val iddokter: String = "",
    val namadokter: String = "",
    val spesialisasi: String = "",
    val kontak: String = "",


    )

fun InsertDokterEvent.toDkr(): Dokter = Dokter(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)

fun Dokter.toDokterStateDkr():InsertDokterState = InsertDokterState(
    insertDokterEvent = toInsertDokterEvent()
)

fun Dokter.toInsertDokterEvent():InsertDokterEvent = InsertDokterEvent(
    iddokter = iddokter,
    namadokter = namadokter,
    spesialisasi = spesialisasi,
    kontak = kontak
)