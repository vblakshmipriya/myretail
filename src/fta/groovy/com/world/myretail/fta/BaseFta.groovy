package com.world.myretail.fta

import com.world.myretail.MyretailApplication
import org.springframework.boot.web.client.RestTemplateBuilder

import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit

import javax.annotation.Resource

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ContextConfiguration
import org.springframework.web.client.RestTemplate

import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

@ComponentScan(value = ['com.world.myretail'])
class BaseFta extends Specification {

  @Shared
  @AutoCleanup
  ConfigurableApplicationContext context

  RestTemplate restTemplate =  new RestTemplateBuilder().rootUri('http://localhost:8080').build()

  void setupSpec() {
      Future future = Executors.newSingleThreadExecutor().submit(
          new Callable() {
            ConfigurableApplicationContext call() throws Exception {
              return new MyretailApplication()
                  .configure(new SpringApplicationBuilder(MyretailApplication))
                  .run()
            }
          })
      context = future.get(300, TimeUnit.SECONDS)
    }
}