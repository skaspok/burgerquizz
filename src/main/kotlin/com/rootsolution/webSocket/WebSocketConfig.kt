package com.rootsolution.webSocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(config: MessageBrokerRegistry?) {
        config!!.enableSimpleBroker("/bg")
        config.setApplicationDestinationPrefixes("/server")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry?) {
        registry!!.addEndpoint("/burgerquizz-websocket").withSockJS()
    }

}