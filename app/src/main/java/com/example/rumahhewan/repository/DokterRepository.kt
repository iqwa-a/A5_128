package com.example.rumahhewan.repository

import com.example.rumahhewan.Service.DokterService
import com.example.rumahhewan.Service.HewanService
import com.example.rumahhewan.model.Dokter
import com.example.rumahhewan.model.Hewan
import okio.IOException

interface DokterRepository{
    suspend fun getDokter(): List<Dokter>
    suspend fun insertDokter(dokter: Dokter)
    suspend fun updateDokter(iddokter: String, dokter: Dokter)
    suspend fun deleteDokter(iddokter: String)
    suspend fun getDokterById(iddokter: String): Dokter
}

class NetworkDokterRepository(private val dokterApiService: DokterService) : DokterRepository{
    override suspend fun insertDokter(dokter: Dokter){
        dokterApiService.insertDokter(dokter)
    }
    override suspend fun updateDokter(iddokter: String, dokter: Dokter){
        dokterApiService.updateDokter(iddokter, dokter)
    }

    override suspend fun deleteDokter(iddokter: String){
        try {
            val response = dokterApiService.deleteDokter(iddokter)
            if (!response.isSuccessful){
                throw IOException("Failed to delete Dokter. HTTP Status Code: ${response.code()}")
            } else{
                response.message()
                println(response.message())
            }
        }catch (e: Exception){
            throw e
        }
    }

    override suspend fun getDokter(): List<Dokter> = dokterApiService.getDokter()
    override suspend fun getDokterById(iddokter: String): Dokter {
        return dokterApiService.getDokterById(iddokter)
    }
}