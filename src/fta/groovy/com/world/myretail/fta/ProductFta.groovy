package com.world.myretail.fta

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import spock.lang.Stepwise

@Stepwise
class ProductFta extends BaseFta {

  def 'get product responds with 200'() {
    when:
    ResponseEntity response = restTemplate.exchange('/v1/product/id/13860427', HttpMethod.GET, new HttpEntity(null, new HttpHeaders()), Map)

    then:
    response.statusCode == HttpStatus.OK
    response.body.name == 'Conan the Barbarian (dvd_video)'
    response.body.current_price == [value: 0.0, currency_code: '']
    response.body.links == [[rel: 'self', href: 'http://localhost:8080/v1/product/id/13860427']]
  }

  def 'get unknown product record responds with 404'() {
    when:
    ResponseEntity response = restTemplate.exchange('/v1/product/id/unknown', HttpMethod.GET, new HttpEntity(null, new HttpHeaders()), Map)

    then:
    response.statusCode == HttpStatus.NOT_FOUND
    response.body.status == 404
    response.body.message == 'Product not found for id=unknown'
  }

  def 'update product price'() {
    when:
    ResponseEntity putResponse = restTemplate.exchange('/v1/product/id/13860428', HttpMethod.PUT, new HttpEntity([price: [value: 120.00, code: 'USD']], new HttpHeaders()), Map)

    then:
    putResponse.statusCode == HttpStatus.ACCEPTED
    putResponse.body.id == '13860428'
    putResponse.body.current_price == [value: 120.00, currency_code: 'USD']
    putResponse.body.links == [[rel: 'self', href: 'http://localhost:8080/v1/product/id/13860428']]

    when:
    ResponseEntity response = restTemplate.exchange('/v1/product/id/13860428', HttpMethod.GET, new HttpEntity(null, new HttpHeaders()), Map)

    then:
    response.statusCode == HttpStatus.OK
    response.body.name == 'The Big Lebowski (Blu-ray)'
    response.body.current_price == [value: 120.00, currency_code: 'USD']
  }
}