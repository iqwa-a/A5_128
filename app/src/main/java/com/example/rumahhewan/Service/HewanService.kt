package com.example.rumahhewan.Service

import com.example.rumahhewan.model.Hewan
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface HewanService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("bacahewan.php")
    suspend fun getHewan(): List<Hewan>

    @GET("bacaidhewan.php/{id_hewan}")
    suspend fun getHewanById(@Query("id_hewan") idhewan:String):Hewan

    @POST("inserthewan.php")
    suspend fun insertHewan(@Body hewan: Hewan)

    @PUT("edithewan.php/{id_hewan}")
    suspend fun updatehewan(@Query("id_hewan")idhewan: String, @Body hewan: Hewan)

    @DELETE("deletehewan.php/{id_hewan}")
    suspend fun deleteHewan(@Query("id_hewan")idhewan: String):retrofit2.Response<Void>

}