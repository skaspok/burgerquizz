package com.rootsolution.burgerquizz.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component

@Component
@PropertySource("classpath:application.properties")
class GlobalProperties {

    @Value("\${videos.path}")
    val videoPath: String =""
}