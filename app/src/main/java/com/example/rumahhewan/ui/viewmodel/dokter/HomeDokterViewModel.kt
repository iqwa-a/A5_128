package com.example.rumahhewan.ui.viewmodel.dokter

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.repository.DokterRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeDokterState{
    data class Success(val dokter : List<Dokter>): HomeDokterState()
    object Error: HomeDokterState()
    object Loading: HomeDokterState()
}

class HomeDokterViewModel (private val dkr: DokterRepository): ViewModel(){
    var dkrDokterState: HomeDokterState by mutableStateOf(HomeDokterState.Loading)
        private set

    init {
        getDkr()
    }

    fun getDkr(){
        viewModelScope.launch {
            dkrDokterState = HomeDokterState.Loading
            dkrDokterState = try {
                HomeDokterState.Success(dkr.getDokter())
            }catch (e:IOException){
                HomeDokterState.Error
            }catch (e: HttpException){
                HomeDokterState.Error
            }
        }
    }

    fun deleteDkr(iddokter:String){
        viewModelScope.launch {
            try {
                dkr.deleteDokter(iddokter)
            }catch (e: IOException){
                HomeDokterState.Error
            }catch (e: HttpException){
                HomeDokterState.Error
            }
        }
    }
}