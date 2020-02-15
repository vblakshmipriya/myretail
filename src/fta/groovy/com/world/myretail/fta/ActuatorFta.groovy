package com.world.myretail.fta

class ActuatorFta extends BaseFta {

  def 'actuator works'() {
    when:
    Map responseBody =  restTemplate.getForObject('/actuator',Map)

    then:
    responseBody.'_links'.self.href == 'http://localhost:8080/actuator'
  }
}