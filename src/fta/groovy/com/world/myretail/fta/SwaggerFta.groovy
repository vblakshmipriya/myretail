package com.world.myretail.fta

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SwaggerFta extends BaseFta {

  def 'swagger api-docs returns json'() {
    when:
    ResponseEntity response = restTemplate.exchange('/v2/api-docs', HttpMethod.GET, new HttpEntity(null, new HttpHeaders()), Map)

    then:
    response.statusCode == HttpStatus.OK
    response.body.info.title == 'Api Documentation'
    response.body.host == 'localhost:8080'
  }

  def 'swagger ui is available '() {
    when:
    ResponseEntity response = restTemplate.exchange('/swagger-ui.html', HttpMethod.GET, new HttpEntity(null, new HttpHeaders()), Void)

    then:
    response.statusCode == HttpStatus.OK
  }
}