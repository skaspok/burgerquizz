package com.rootsolution.burgerquizz.config

import com.rootsolution.burgerquizz.webSocket.event.PresenceEventListener
import org.springframework.context.annotation.*
import org.springframework.messaging.simp.SimpMessagingTemplate

@Configuration
class ChatConfig {

//    @Autowired
//    private val chatProperties: ChatProperties? = null

    @Bean
    @Description("Tracks user presence (join / leave) and broacasts it to all connected users")
    fun presenceEventListener(messagingTemplate: SimpMessagingTemplate): PresenceEventListener {

        val presence = PresenceEventListener()
//        presence.setLoginDestination( chatProperties!!.getDestinations().getLogin() )
//        presence.setLogoutDestination( chatProperties!!.getDestinations().getLogout() )
        return presence
    }

//    @Bean
//    @Description("Keeps connected users")
//    fun participantRepository(): ParticipantRepository {
//        return ParticipantRepository()
//    }
//
//    @Bean
//    @Scope(value = "websocket", proxyMode = ScopedProxyMode.TARGET_CLASS)
//    @Description("Keeps track of the level of profanity of a websocket session")
//    fun sessionProfanity(): SessionProfanity {
//        return SessionProfanity(chatProperties!!.getMaxProfanityLevel())
//    }
//
//    @Bean
//    @Description("Utility class to check the number of profanities and filter them")
//    fun profanityFilter(): ProfanityChecker {
//        val checker = ProfanityChecker()
//        checker.setProfanities(chatProperties!!.getDisallowedWords())
//        return checker
//    }
//
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Description("Embedded Redis used by Spring Session")
//    @Throws(IOException::class)
//    fun redisServer(@Value("\${redis.embedded.port}") port: Int): RedisServer {
//        return RedisServer(port)
//    }
}
