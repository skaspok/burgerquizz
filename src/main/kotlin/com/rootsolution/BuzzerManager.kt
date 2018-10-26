package com.rootsolution

import com.rootsolution.display.UiClass
import com.rootsolution.webSocket.TeamManager
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class BuzzerManager(private val teamManager: TeamManager) {

    //Not meant to be stateless
    companion object {
        private var lock: Boolean = false
        private val logger = LoggerFactory.getLogger(BuzzerManager::class.qualifiedName)
        private const val TIMEOUT = 2500
    }


    /**
     * True if Buzz wins
     * Also displays which team buzzed on the main display
     */
    fun handleNewBuzz(destToken: String): Boolean {

        if (!lock) {
            val team = teamManager.getTeam(destToken)
            UiClass.instance!!.buzz(team!!.teamName)
            lock = true

            val thread = Thread(Runnable {
                Thread.sleep(TIMEOUT.toLong())
                lock = false
                UiClass.instance!!.buzz(null)
            })
            thread.start()

            return true
        } else {
            logger.info("Useless buzz")
        }

        return false

    }
}