package com.world.myretail.product.service

import javax.annotation.Resource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

import com.world.myretail.product.domain.Product
import com.world.myretail.product.domain.Price
import com.world.myretail.product.exception.NotFoundException
import com.world.myretail.product.repository.PriceRepository

@Component
class ProductService {

  @Resource
  RestTemplate redskyRestTemplate

  @Autowired
  PriceRepository priceRepository

  @Value('${application.host}')
  String host

  Product getProduct(String id) {
    try {
      Map product = redskyRestTemplate.getForObject("/v2/pdp/tcin/$id", Map)?.product
      Price price = priceRepository.findById(id).orElse(null)
      String href = "$host/v1/product/id/$id"
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
                      href: href
                  ]
              ]
      )
    } catch (HttpClientErrorException e) {
      throw new NotFoundException("Product not found for id=$id")
    }
  }

  Product upsertProductPrice(String id, Map productPrice) {
    priceRepository.save(new Price(product_id: id, price_record: productPrice.price, last_update_on: new Date()))
    String href = "$host/v1/product/id/$id"
    new Product(
        id: id,
        current_price: [
            value        : productPrice.price.value,
            currency_code: productPrice.price.code
        ],
        links:
            [
                [
                    rel : 'self',
                    href: href
                ]
            ]
    )
  }
}
