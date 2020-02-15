package com.world.myretail.product.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import com.world.myretail.product.model.Product

@RestController
@RequestMapping('/v1/product/id/{id}')
class ProductController {

  @GetMapping
  Product getProduct() {
    new Product(
        name: 'The Big Lebowski (Blu-ray)',
        current_price: [
            value        : 12.34,
            currency_code: 'USD'
        ])
  }
}
