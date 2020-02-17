package com.world.myretail

import org.springframework.context.annotation.Bean

import spock.lang.Specification

class MyretailApplicationSpec extends Specification {
  def 'api docket bean'() {
    expect:
    MyretailApplication.getDeclaredMethod('apiDocket').isAnnotationPresent(Bean)
  }
}
