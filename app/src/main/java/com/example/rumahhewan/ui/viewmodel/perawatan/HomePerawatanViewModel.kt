package com.example.rumahhewan.ui.viewmodel.perawatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.model.Perawatan
import com.example.rumahhewan.repository.HewanRepository
import com.example.rumahhewan.repository.PerawatanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePerawatanState{
    data class Success(val perawatan : List<Perawatan>): HomePerawatanState()
    object Error: HomePerawatanState()
    object Loading: HomePerawatanState()
}

class HomePerawatanViewModel (private val pwt: PerawatanRepository): ViewModel(){
    var pwtPerawatanState: HomePerawatanState by mutableStateOf(HomePerawatanState.Loading)
        private set

    init {
        getPwt()
    }

    fun getPwt(){
        viewModelScope.launch {
            pwtPerawatanState = HomePerawatanState.Loading
            pwtPerawatanState = try {
                HomePerawatanState.Success(pwt.getPerawatan())
            }catch (e:IOException){
                HomePerawatanState.Error
            }catch (e: HttpException){
                HomePerawatanState.Error
            }
        }
    }

    fun deletePwt(idperawatan:String){
        viewModelScope.launch {
            try {
                pwt.deletePerawatan(idperawatan)
            }catch (e: IOException){
                HomePerawatanState.Error
            }catch (e: HttpException){
                HomePerawatanState.Error
            }
        }
    }
}