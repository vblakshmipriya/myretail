package com.world.myretail.product.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class Product {
  String id

  String name

  Map current_price

  List<Map> links
}
