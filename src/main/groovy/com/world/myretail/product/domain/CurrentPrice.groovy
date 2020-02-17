package com.world.myretail.product.domain

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class CurrentPrice {
  double value

  String currency_code
}
