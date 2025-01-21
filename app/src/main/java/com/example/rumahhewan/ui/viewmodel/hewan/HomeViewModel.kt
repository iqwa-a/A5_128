package com.example.rumahhewan.ui.viewmodel.hewan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.rumahhewan.model.Hewan
import com.example.rumahhewan.repository.HewanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeHewanState{
    data class Success(val hewan : List<Hewan>): HomeHewanState()
    object Error: HomeHewanState()
    object Loading: HomeHewanState()
}

class HomeHewanViewModel (private val hwn: HewanRepository): ViewModel(){
    var hwnHewanState: HomeHewanState by mutableStateOf(HomeHewanState.Loading)
        private set

    init {
        getHwn()
    }

    fun getHwn(){
        viewModelScope.launch {
            hwnHewanState = HomeHewanState.Loading
            hwnHewanState = try {
                HomeHewanState.Success(hwn.getHewan())
            }catch (e:IOException){
                HomeHewanState.Error
            }catch (e: HttpException){
                HomeHewanState.Error
            }
        }
    }

    fun deleteHwn(idhewan:String){
        viewModelScope.launch {
            try {
                hwn.deleteHewan(idhewan)
            }catch (e: IOException){
                HomeHewanState.Error
            }catch (e: HttpException){
                HomeHewanState.Error
            }
        }
    }
}