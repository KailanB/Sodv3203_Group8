package com.example.skillswapapp

import android.app.Application
import com.example.skillswapapp.data.AppContainer
import com.example.skillswapapp.data.AppDataContainer

class SkillSwapApplication : Application()  {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
