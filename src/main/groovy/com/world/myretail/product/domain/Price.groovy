package com.world.myretail.product.domain

import org.springframework.data.annotation.Transient
import org.springframework.data.cassandra.core.cql.PrimaryKeyType
import org.springframework.data.cassandra.core.mapping.PrimaryKey
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn
import org.springframework.data.cassandra.core.mapping.Table

import com.fasterxml.jackson.annotation.JsonFormat

import groovy.transform.EqualsAndHashCode

@Table
@EqualsAndHashCode
class Price {
  @PrimaryKey
  String product_id

  Map<String, String> price_record

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
  Date updateDate
}

