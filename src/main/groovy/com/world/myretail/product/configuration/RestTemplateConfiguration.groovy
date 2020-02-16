package com.world.myretail.product.configuration
import javax.annotation.Resource
import javax.net.ssl.SSLContext

import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.DefaultUriBuilderFactory
import org.springframework.web.util.UriTemplateHandler

import com.fasterxml.jackson.databind.ObjectMapper

@Configuration
class RestTemplateConfiguration {

  @Resource
  ObjectMapper objectMapper

  @Bean
  MappingJackson2HttpMessageConverter jacksonConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter()
    converter.setObjectMapper(objectMapper)
    converter
  }

  @Resource
  MappingJackson2HttpMessageConverter jacksonConverter

  @Bean
  RestTemplate redskyRestTemplate(
      @Value('${redsky.host}') String host,
      @Value('${redsky.timeout.ms}') int timeout
  ) {
    buildRestTemplate(host, timeout).build()
  }

  RestTemplateBuilder buildRestTemplate(String host, int timeout) {
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(timeout)
        .setConnectionRequestTimeout(timeout)
        .setSocketTimeout(timeout)
        .build()

    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig)
        .setMaxConnPerRoute(20)

    UriTemplateHandler uriFactory = new DefaultUriBuilderFactory()
    uriFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.TEMPLATE_AND_VALUES)

    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClientBuilder.build())

    new RestTemplateBuilder()
        .requestFactory({requestFactory})
        .rootUri(host)
        .uriTemplateHandler(uriFactory)
        .messageConverters(jacksonConverter)
  }
}
