package com.world.myretail.product.repository

import org.springframework.data.cassandra.repository.CassandraRepository

import com.world.myretail.product.domain.Price

interface PriceRepository extends CassandraRepository<Price, String> {
}
