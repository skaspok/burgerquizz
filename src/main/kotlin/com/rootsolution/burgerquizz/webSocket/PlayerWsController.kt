package com.rootsolution.burgerquizz.webSocket

import com.rootsolution.burgerquizz.game.BuzzerManager
import com.rootsolution.burgerquizz.game.Team
import com.rootsolution.burgerquizz.game.TeamManager
import org.apache.commons.lang3.StringUtils
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller


@Controller
class PlayerWsController(private val teamManager: TeamManager,
                         private val buzzerManager: BuzzerManager) {

    private val logger = LoggerFactory.getLogger(PlayerWsController::class.qualifiedName)

    //Players
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