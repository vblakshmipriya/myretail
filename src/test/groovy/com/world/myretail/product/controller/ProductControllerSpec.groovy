package com.world.myretail.product.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import spock.lang.Specification
import spock.lang.Unroll

import com.world.myretail.JsonUtils
import com.world.myretail.product.domain.Product
import com.world.myretail.product.service.ProductService

class ProductControllerSpec extends Specification {
  MockMvc mockMvc
  ProductService mockProductService = Mock()

  void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(new ProductController(productService: mockProductService)).build()
  }

  @Unroll
  def 'get product returns product name #name and price'() {
    when:
    MvcResult result = mockMvc.perform(get("/v1/product/id/$id")).andReturn()

    then:
    1 * mockProductService.getProduct(id, 'http://localhost:8080') >> new Product(name: name, current_price: [value: 12.34, currency_code: 'USD'], links: [[rel: 'self', href: 'someurl']])
    result.response.status == HttpStatus.OK.value()
    JsonUtils.assertJsonEquals("""
            {
              "name":"$name",
              "current_price":
              {
                "value":12.34,
                "currency_code":"USD"
              },
              "links": [
              {
                "rel": "self",
                "href": "someurl"
              }]
            }""", result.response.contentAsString)

    where:
    id      | name
    '12345' | 'The Big Lebowski (Blu-ray)'
    '12346' | 'some name'
  }
}
