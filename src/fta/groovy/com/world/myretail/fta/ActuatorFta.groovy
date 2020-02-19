package com.world.myretail.fta

class ActuatorFta extends BaseFta {

  def 'actuator works'() {
    when:
    Map responseBody = restTemplate.getForObject('/actuator', Map)

    then:
    responseBody.'_links'.self.href == 'http://localhost:8080/actuator'
  }

  def 'health-check works as expected for cassandra'() {
    when:
    Map responseBody = restTemplate.getForObject('/actuator/health', Map)

    then:
    responseBody.components.cassandra ==
        [
            status : 'UP',
            details: [
                version: '3.11.1.2323'
            ]
        ]
  }

  def 'health-check works as expected for redsky'() {
    when:
    Map responseBody = restTemplate.getForObject('/actuator/health', Map)

    then:
    responseBody.components.redSkyHealthCheck ==
        [
            status : 'UP',
            details: [
                RedSkyServer: 'is available'
            ]
        ]
  }
}