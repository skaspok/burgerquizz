package com.rootsolution.webSocket.event

import com.rootsolution.Team
import com.rootsolution.webSocket.TeamManager
import org.apache.commons.lang3.StringUtils.isBlank
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent
import java.util.*

/**
 * Listener to track user presence.
 * Sends notifications to the login destination when a connected event is received
 * and notifications to the logout destination when a disconnect event is received
 *
 * @author Sergi Almar
 */
class PresenceEventListener( ) {
    @Autowired
    private val teamManager: TeamManager? =null

    private val logger = LoggerFactory.getLogger(PresenceEventListener::class.qualifiedName)

    private var loginDestination: String? = null

    private var logoutDestination: String? = null

    @EventListener
    private fun handleSessionConnected(event: SessionConnectEvent) {

        val token: String = event.message.headers["simpSessionId"] as String;

        if(isBlank(token)){
            throw Exception("Connection with blank token")
        }

        logger.info("Connect " + token)
//        val newTeam : Team = teamManager!!.newTeam(token)

//        val loginEvent = LoginEvent("myusername")
//        messagingTemplate.convertAndSend("loginDestination", loginEvent)

    }

    @EventListener
    private fun handleSessionDisconnect(event: SessionDisconnectEvent) {

        logger.info("Disconnect")

//        Optional.ofNullable(participantRepository.getParticipant(event.sessionId))
//                .ifPresent({ login ->
//                    messagingTemplate.convertAndSend(logoutDestination, LogoutEvent(login.getUsername()))
//                    participantRepository.removeParticipant(event.sessionId)
//                })
    }

    fun setLoginDestination(loginDestination: String) {
        this.loginDestination = loginDestination
    }

    fun setLogoutDestination(logoutDestination: String) {
        this.logoutDestination = logoutDestination
    }
}
