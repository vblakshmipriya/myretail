package com.world.myretail.product.configuration

import javax.annotation.Resource

import org.springframework.boot.actuate.health.Health
import org.springframework.boot.actuate.health.HealthIndicator
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
public class RedSkyHealthCheck implements HealthIndicator {
  @Resource
  RestTemplate redskyRestTemplate

  @Override
  Health health() {
    if (redskyRestTemplate.getForEntity('/', Void).statusCode == HttpStatus.OK) {
      return Health.up().withDetail("RedSkyServer", "is available").build()
    }
    return Health.down().withDetail("RedSkyServer", "is not available").build()
  }
}