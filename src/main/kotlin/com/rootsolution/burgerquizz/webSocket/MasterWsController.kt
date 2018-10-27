package com.rootsolution.burgerquizz.webSocket

import com.rootsolution.burgerquizz.config.GlobalProperties
import com.rootsolution.burgerquizz.display.VideoManager
import com.rootsolution.burgerquizz.display.UiClass
import com.rootsolution.burgerquizz.game.TeamManager
import com.rootsolution.burgerquizz.webSocket.DTO.PointsTransfertObject
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class MasterWsController(private val teamManager: TeamManager,
                         private val videoManager: VideoManager){

    companion object {
        private val logger = LoggerFactory.getLogger(MasterWsController::class.qualifiedName)
        private var masterConnected :Boolean = false
    }

//    @Autowired
//    lateinit var globalProperties: GlobalProperties

    //Master
    @MessageMapping("/start-video")
    fun startVideo(file: String){
        logger.info("start Video")
        UiClass.instance!!.playVideo( file)
    }

    @MessageMapping("/points")
    fun updatePoints(points: PointsTransfertObject){
        teamManager.updatePoints(points)
    }

    @MessageMapping("/get-videos")
    @SendTo("/bg/videos")
    fun getVideos(): String{
        return videoManager.getVideoList()
    }
}
