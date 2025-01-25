package com.example.rumahhewan.Service

import com.example.rumahhewan.model.Jenis
import com.example.rumahhewan.model.Perawatan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PerawatanService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("bacaperawatan.php")
    suspend fun getPerawatan(): List<Perawatan>

    @GET("bacaidperawatan.php")
    suspend fun getPerawatanById(@Query("id_perawatan") idperawatan:String): Perawatan

    @POST("insertperawatan.php")
    suspend fun insertPerawatan(@Body idperawatan: Perawatan)

    @PUT("editperawatan.php/")
    suspend fun updatePerawatan(@Query("id_perawatan")idperawatan: String, @Body perawatan: Perawatan)

    @DELETE("deleteperawatan.php/{id_perawatan}")
    suspend fun deletePerawatan(@Query("id_perawatan")idperawatan: String):retrofit2.Response<Void>

}