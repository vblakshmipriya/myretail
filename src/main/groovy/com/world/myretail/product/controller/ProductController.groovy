package com.world.myretail.product.controller

import javax.annotation.Resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.world.myretail.product.domain.Product
import com.world.myretail.product.service.ProductService

@RestController
@RequestMapping('/v1/product/id/{id}')
class ProductController {
  @Resource
  ProductService productService

  @GetMapping
  Product getProduct(@PathVariable('id') String id) {
    productService.getProduct(id, 'http://localhost:8080')
  }
}
