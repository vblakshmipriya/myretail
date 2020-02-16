package com.world.myretail.product.configuration

import javax.annotation.Resource

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.convert.converter.Converter
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions
import org.springframework.data.convert.CustomConversions

import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
class CassandraConfiguration extends AbstractCassandraConfiguration {
  @Value('${spring.data.cassandra.keyspaceName}')
  String keyspaceName
  @Value('${spring.data.cassandra.port}')
  int port
  @Value('${spring.data.cassandra.contact-points}')
  String contactPoints

  @Resource
  ObjectMapper objectMapper

  @Override
  CustomConversions customConversions() {
    Converter readConverter = new Converter<String, Map>() {
      @Override
      Map convert(String source) {
        objectMapper.readValue(source.bytes, Map)
      }
    }

    Converter writeConverter = new Converter<Map, String>() {
      @Override
      String convert(Map source) {
        objectMapper.writeValueAsString(source)
      }
    }

    new CassandraCustomConversions([readConverter, writeConverter])
  }
}
