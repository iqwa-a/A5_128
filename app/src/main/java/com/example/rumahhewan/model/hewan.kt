package com.example.rumahhewan.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class hewan(
    @SerialName("id_hewan") val idHewan: String,  // Menyesuaikan dengan kolom id_hewan di DB
    @SerialName("nama_hewan") val namaHewan: String,  // Menyesuaikan dengan kolom nama_hewan
    @SerialName("jenis_hewan_id") val jenisHewanId: String,  // Menyesuaikan dengan kolom jenis_hewan_id
    @SerialName("kontak_pemilik") val kontakPemilik: String,  // Menyesuaikan dengan kolom kontak_pemilik
    @SerialName("tanggal_lahir") val tanggalLahir: String,  // Menyesuaikan dengan kolom tanggal_lahir
    val pemilik: String,  // Menyesuaikan dengan kolom pemilik
    @SerialName("catatan_kesehatan") val catatanKesehatan: String  // Menyesuaikan dengan kolom catatan_kesehatan

)
