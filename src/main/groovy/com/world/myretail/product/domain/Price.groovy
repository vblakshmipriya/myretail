package com.world.myretail.product.domain

import org.springframework.data.annotation.Transient
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper

import groovy.transform.EqualsAndHashCode

@Table
@EqualsAndHashCode
class Price {
  @PrimaryKey
  String product_id

  String price_record

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  Date last_update_on

  @Transient
  private ObjectMapper objectMapper = new ObjectMapper()

  void setCurrentPrice(CurrentPrice currentPrice) {
    this.price_record = objectMapper.writeValueAsString(currentPrice)
  }

  CurrentPrice getCurrentPrice() {
    if (price_record) {
      objectMapper.readValue(price_record, new TypeReference<CurrentPrice>() {})
    }
  }
}

