package com.rootsolution.webSocket

import com.rootsolution.Team
import com.rootsolution.display.UiClass
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.web.util.HtmlUtils
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller


@Controller
class MessageController(private val teamManager:TeamManager) {

    private val logger = LoggerFactory.getLogger("MessageController")

    //Players
    //TODO : different file?

    @MessageMapping("/buzz/{destToken}")
    @SendTo("/bg/buzz-result/{destToken}")
    fun buzz(@DestinationVariable destToken: String): String{
        logger.info("Buzz " + destToken)

        val team = teamManager.getTeam(destToken)
        UiClass.instance!!.buzz(team!!.teamName)

        return "OK"
    }


    @MessageMapping("/new-player/{destToken}")
    @SendTo("/bg/new-ack/{destToken}")
    fun newPlayer(token: String): String{

        if(StringUtils.isBlank(token)){
            throw Exception("Connection with blank token")
        }

        val newTeam: Team = teamManager.newTeam(token)
        logger.info("New Player affected : "+ newTeam.teamName)

        return newTeam.teamName.toString()
    }


    //Master
    @MessageMapping("/video")
//    @SendTo("/bg/new-ack/{destToken}")
    fun startVideo(){
        logger.info("start Video")
        UiClass.instance!!.playVideo("/home/adrien/Downloads/videoplayback.mp4")
    }


    @MessageMapping("/points")
    fun updatePoints(points: PointsTransfertObject){
        teamManager.updatePoints(points)

    }


}