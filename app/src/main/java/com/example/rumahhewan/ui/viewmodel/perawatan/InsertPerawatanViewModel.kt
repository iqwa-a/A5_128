package com.example.rumahhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.model.Perawatan
import com.example.rumahhewan.repository.JenisHewanRepository
import com.example.rumahhewan.repository.PerawatanRepository
import kotlinx.coroutines.launch

class InsertPerawatanViewModel (private val pwt: PerawatanRepository): ViewModel(){
    var perawatanState by mutableStateOf(InsertPerawatanState())
        private set

    fun updateInsertPwtState(insertPerawatanEvent: InsertPerawatanEvent){
        perawatanState = InsertPerawatanState(insertPerawatanEvent = insertPerawatanEvent)
    }

    suspend fun insertPwt(){
        viewModelScope.launch {
            try {
                pwt.insertPerawatan(perawatanState.insertPerawatanEvent.toPwt())
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }
}


data class InsertPerawatanState(
    val insertPerawatanEvent : InsertPerawatanEvent = InsertPerawatanEvent()
)

data class InsertPerawatanEvent(
    val idperawatan: String = "",
    val idhewan: String = "",
    val iddokter: String = "",
    val tanggalperawatan: String = "",
    val detailperawatan: String = "",


    )

fun InsertPerawatanEvent.toPwt(): Perawatan = Perawatan(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan,
    )

fun Perawatan.toPerawatanStatePwt():InsertPerawatanState = InsertPerawatanState(
    insertPerawatanEvent = toInsertPerawatanEvent()
)

fun Perawatan.toInsertPerawatanEvent():InsertPerawatanEvent = InsertPerawatanEvent(
    idperawatan = idperawatan,
    idhewan = idhewan,
    iddokter = iddokter,
    tanggalperawatan = tanggalperawatan,
    detailperawatan = detailperawatan,

    )