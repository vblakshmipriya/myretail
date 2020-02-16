package com.world.myretail.product.service

import javax.annotation.Resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

import com.world.myretail.product.domain.Product
import com.world.myretail.product.domain.Price
import com.world.myretail.product.repository.PriceRepository

@Component
class ProductService {

  @Resource
  RestTemplate redskyRestTemplate

  @Autowired
  PriceRepository priceRepository

  Product getProduct(String id, String host) {
    Map product = redskyRestTemplate.getForObject("/v2/pdp/tcin/$id", Map)?.product
    Price price = priceRepository.findById(id).orElse(null)
    new Product(
        name: product.item.product_description.title,
        current_price: [
            value        : price ? price.price_record.value : 0.0,
            currency_code: price ? price.price_record.code : ''
        ],
        links:
            [
                [
                    rel : 'self',
                    href: "$host/v1/product/id/$id"
                ]
            ]
    )
  }
}
