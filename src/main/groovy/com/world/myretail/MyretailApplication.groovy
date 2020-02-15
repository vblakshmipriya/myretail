package com.world.myretail

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.PropertySource
import org.springframework.context.annotation.PropertySources

@SpringBootApplication(scanBasePackages = ['com.world.myretail'])
class MyretailApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        application.sources(MyretailApplication)
    }
    static void main(String[] args) {
        SpringApplication.run(MyretailApplication, args)
    }

}
