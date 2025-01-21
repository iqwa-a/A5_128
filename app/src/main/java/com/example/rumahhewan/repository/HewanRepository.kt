package com.example.rumahhewan.repository

import com.example.rumahhewan.model.Hewan
import okio.IOException

interface HewanRepository{
    suspend fun getHewan(): List<Hewan>
    suspend fun insertHewan(hewan: Hewan)
    suspend fun updateHewan(idhewan: String, hewan: Hewan)
    suspend fun deleteHewan(idhewan: String)
    suspend fun getHewanById(idhewan: String): Hewan
}

class NetworkHewanRepository(private val hewanApiService: HewanService) : HewanRepository{
    override suspend fun insertHewan(hewan: Hewan){
        hewanApiService.insertHewan(hewan)
    }
    override suspend fun updateHewan(idhewan: String, hewan: Hewan){
        hewanApiService.updateHewan(idhewan, hewan)
    }

    override suspend fun deleteHewan(idhewan: String){
        try {
            val response = hewanApiService.deleteHewan(idhewan)
            if (!response.isSuccessful){
                throw IOException("Failed to delete mahasiswa. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getHewan(): List<Hewan> = hewanApiService.getHewan()
    override suspend fun getHewanById(idhewan: String): Hewan {
        return hewanApiService.geHewanById(idhewan)
    }
}