package com.world.myretail.product.service

import org.springframework.web.client.RestTemplate

import spock.lang.Specification

import com.world.myretail.product.domain.Price
import com.world.myretail.product.domain.Product
import com.world.myretail.product.repository.PriceRepository

class ProductServiceSpec extends Specification {
  RestTemplate mockRestTemplate = Mock()
  PriceRepository mockPriceRepository = Mock()
  ProductService productService = new ProductService(redskyRestTemplate: mockRestTemplate, priceRepository: mockPriceRepository)

  def 'get product'() {
    when:
    Product product = productService.getProduct('12345', 'host')

    then:
    1 * mockRestTemplate.getForObject('/v2/pdp/tcin/12345', Map) >> [product: [item: [product_description: [title: 'product_name']]]]
    1 * mockPriceRepository.findById('12345') >> Optional.of(new Price(product_id: '12345', price_record: [value: 123.00, code: 'USD']))
    product.name == 'product_name'
    product.current_price == [value: 123.00, currency_code: 'USD']
    product.links.first().rel == 'self'
    product.links.first().href == 'host/v1/product/id/12345'
  }

  def 'product price is zero when cassandra returns empty'() {
    when:
    Product product = productService.getProduct('12345','host')

    then:
    1 * mockRestTemplate.getForObject('/v2/pdp/tcin/12345', Map) >> [product: [item: [product_description: [title: 'product_name']]]]
    1 * mockPriceRepository.findById('12345') >> Optional.empty()
    product.name == 'product_name'
    product.current_price == [value: 0.0, currency_code: '']
    product.links.first().rel == 'self'
    product.links.first().href == 'host/v1/product/id/12345'
  }
}
