package com.world.myretail.product.service

import org.slf4j.MDC
import org.springframework.web.client.RestTemplate

import spock.lang.Specification

import com.world.myretail.product.domain.CurrentPrice
import com.world.myretail.product.domain.Price
import com.world.myretail.product.domain.Product
import com.world.myretail.product.repository.PriceRepository

class ProductServiceSpec extends Specification {
  RestTemplate mockRestTemplate = Mock()
  PriceRepository mockPriceRepository = Mock()
  ProductService productService = new ProductService(redskyRestTemplate: mockRestTemplate, priceRepository: mockPriceRepository, host: 'host')

  def 'get product'() {
    when:
    Product product = productService.getProduct('12345')

    then:
    1 * mockRestTemplate.getForObject('/v2/pdp/tcin/12345', Map) >> [product: [item: [product_description: [title: 'product_name']]]]
    1 * mockPriceRepository.findById('12345') >> Optional.of(new Price(product_id: '12345', price_record: "{\"value\":120.0,\"currency_code\":\"USD\"}"))
    product.name == 'product_name'
    product.current_price.value == 120.00
    product.current_price.currency_code == 'USD'
    product.links.first().rel == 'self'
    product.links.first().href == 'host/v1/product/id/12345'
  }

  def 'product price is null when cassandra returns empty'() {
    when:
    Product product = productService.getProduct('12345')

    then:
    1 * mockRestTemplate.getForObject('/v2/pdp/tcin/12345', Map) >> [product: [item: [product_description: [title: 'product_name']]]]
    1 * mockPriceRepository.findById('12345') >> Optional.empty()
    product.name == 'product_name'
    product.current_price == null
    product.links.first().rel == 'self'
    product.links.first().href == 'host/v1/product/id/12345'
  }

  def 'update product price and return product'() {
    given:
    Date mockDate = GroovyMock(global: true, Date)
    when:
    Product product = productService.upsertProductPrice('12345', new CurrentPrice(value: 10.00, currency_code: 'usd'))

    then:
    1 * new Date() >> mockDate
    1 * mockPriceRepository.save(new Price(product_id: '12345', price_record: "{\"value\":10.0,\"currency_code\":\"usd\"}", last_update_on: mockDate))
    product.id == '12345'
    product.current_price.value == 10.0
    product.current_price.currency_code == 'usd'
    product.links.first().rel == 'self'
    product.links.first().href == 'host/v1/product/id/12345/price'
  }
}
