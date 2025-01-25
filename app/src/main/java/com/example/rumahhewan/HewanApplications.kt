package com.example.rumahhewan

import android.app.Application
import com.example.rumahhewan.repository.AppContainer
import com.example.rumahhewan.repository.HewanContainer

class HewanApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate(){
        super.onCreate()
        container = HewanContainer()
    }
}