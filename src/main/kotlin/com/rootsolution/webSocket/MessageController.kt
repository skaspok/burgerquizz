package com.rootsolution.webSocket

import com.rootsolution.BuzzerManager
import com.rootsolution.GlobalProperties
import com.rootsolution.Team
import com.rootsolution.VideoManager
import com.rootsolution.display.UiClass
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class MessageController( private val teamManager:TeamManager,
                        private val buzzerManager: BuzzerManager) {

    private val logger = LoggerFactory.getLogger(MessageController::class.qualifiedName)

    //Players
    //TODO : different file?

    @MessageMapping("/buzz/{destToken}")
    @SendTo("/bg/buzz-result/{destToken}")
    fun buzz(@DestinationVariable destToken: String): Boolean{
        logger.info("Buzz " + destToken)

        //SendTo not use yet
        return buzzerManager.handleNewBuzz(destToken)
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




}