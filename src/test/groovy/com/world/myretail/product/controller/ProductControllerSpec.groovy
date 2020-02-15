package com.world.myretail.product.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import spock.lang.Specification

class ProductControllerSpec extends Specification {
  MockMvc mockMvc

  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new ProductController()).build()
  }

  def 'get product returns ok'() {
    when:
    MvcResult result = mockMvc.perform(get('/v1/product/id/12345')).andReturn()

    then:
    result.response.status == HttpStatus.OK.value()
  }
}
