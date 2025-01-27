package com.example.rumahhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Hewan(
    @SerialName("id_hewan") val idhewan: String,  // Menyesuaikan dengan kolom id_hewan di DB
    @SerialName("nama_hewan") val namahewan: String,  // Menyesuaikan dengan kolom nama_hewan
    @SerialName("jenis_hewan_id") val jenishewanid: String,  // Menyesuaikan dengan kolom jenis_hewan_id
    @SerialName("kontak_pemilik") val kontakpemilik: String,  // Menyesuaikan dengan kolom kontak_pemilik
    @SerialName("tanggal_lahir") val tanggallahir: String,  // Menyesuaikan dengan kolom tanggal_lahir
    val pemilik: String,  // Menyesuaikan dengan kolom pemilik
    @SerialName("catatan_kesehatan") val catatankesehatan: String  // Menyesuaikan dengan kolom catatan_kesehatan

)
