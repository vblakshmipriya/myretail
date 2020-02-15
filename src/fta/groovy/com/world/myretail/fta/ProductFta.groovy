package com.world.myretail.fta

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class ProductFta extends BaseFta {

  def 'get product responds with 200'() {
    when:
    ResponseEntity response = restTemplate.getForEntity('/v1/product/id/1234567', Map)

    then:
    response.statusCode == HttpStatus.OK
    response.body.name == 'The Big Lebowski (Blu-ray)'
    response.body.current_price == [value: 12.34, currency_code: 'USD']
  }
}