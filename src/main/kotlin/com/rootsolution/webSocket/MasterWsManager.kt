package com.rootsolution.webSocket

import com.rootsolution.GlobalProperties
import com.rootsolution.VideoManager
import com.rootsolution.display.UiClass
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class MasterWsManager(private val teamManager:TeamManager,
                      private val videoManager: VideoManager ){

    companion object {
        private val logger = LoggerFactory.getLogger(MasterWsManager::class.qualifiedName)
        private var masterConnected :Boolean = false
    }


    @Autowired
    lateinit var globalProperties: GlobalProperties

    //Master
    @MessageMapping("/start-video")
    fun startVideo(file: String){
        logger.info("start Video")
        UiClass.instance!!.playVideo(globalProperties.videoPath + file)
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
