package com.world.myretail.product.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put

import javax.servlet.http.HttpServletResponse

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import spock.lang.Specification
import spock.lang.Unroll

import com.world.myretail.JsonUtils
import com.world.myretail.product.domain.CurrentPrice
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
    HttpServletResponse response = mockMvc.perform(get("/v1/product/id/$id")).andReturn().getResponse()

    then:
    1 * mockProductService.getProduct(id) >> new Product(name: name, current_price: [value: 12.34, currency_code: 'USD'], links: [[rel: 'self', href: 'someurl']])
    response.status == HttpStatus.OK.value()
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
            }""", response.contentAsString)

    where:
    id      | name
    '12345' | 'The Big Lebowski (Blu-ray)'
    '12346' | 'some name'
  }

  def 'insert/update price record for product'() {
    when:
    HttpServletResponse response = mockMvc.perform(
        put('/v1/product/id/1234567/price')
            .contentType(MediaType.APPLICATION_JSON)
            .content('{"value": 100.00,"currency_code": "USD"}')
    ).andReturn().getResponse()

    then:
    1 * mockProductService.upsertProductPrice('1234567', _) >>
        new Product(id: '1234567', current_price: [value: 100.00, currency_code: 'USD'], links: [[rel: 'self', href: 'someurl']])
    response.status == HttpStatus.ACCEPTED.value()
    JsonUtils.assertJsonEquals("""
          {
            "id":"1234567",
            "current_price":
            {
              "value":100.00,
              "currency_code":"USD"
            },
            "links":[
            {
              "rel": "self",
              "href": "someurl"
            }
            ]
          }""", response.contentAsString)
  }
}
