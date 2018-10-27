package com.rootsolution.burgerquizz.config

import java.io.IOException

class OsInfoService {

    companion object {

        var isOmxPlayerAvailable: Boolean =  initOmxPlayerAvailable()

        private fun initOmxPlayerAvailable(): Boolean {

            try {
                Runtime.getRuntime().exec("omxplayer -v")
                return true
            } catch (e: IOException) {
                return false

            }
        }


    }


}