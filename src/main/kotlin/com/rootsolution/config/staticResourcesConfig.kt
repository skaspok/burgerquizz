package com.rootsolution.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
class StaticResourceConfiguration : WebMvcConfigurerAdapter() {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
//        registry!!.addResourceHandler("/master/**").addResourceLocations("/resources/static/player.html");
    }
}