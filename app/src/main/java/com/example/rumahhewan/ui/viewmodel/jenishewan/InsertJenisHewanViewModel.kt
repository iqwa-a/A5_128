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

class InsertJenisHewanViewModel (private val jns: JenisHewanRepository): ViewModel(){
    var jenisState by mutableStateOf(InsertJenisState())
        private set

    fun updateInsertJnsState(insertJenisEvent: InsertJenisEvent){
        jenisState = InsertJenisState(insertJenisEvent = insertJenisEvent)
    }

    suspend fun insertJns(){
        viewModelScope.launch {
            try {
                jns.insertJenis(jenisState.insertJenisEvent.toJns())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertJenisState(
    val insertJenisEvent : InsertJenisEvent = InsertJenisEvent()
)

data class InsertJenisEvent(
    val idjenishewan: String = "",
    val namajenishewan: String = "",
    val deskripsi: String = "",


)

fun InsertJenisEvent.toJns(): Jenis = Jenis(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi,


)

fun Jenis.toJenisStateJns():InsertJenisState = InsertJenisState(
    insertJenisEvent = toInsertJenisEvent()
)

fun Jenis.toInsertJenisEvent():InsertJenisEvent = InsertJenisEvent(
    idjenishewan = idjenishewan,
    namajenishewan = namajenishewan,
    deskripsi = deskripsi,

)