package com.world.myretail.product.controller

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest

import com.world.myretail.product.exception.NotFoundException

@RestControllerAdvice
class GlobalControllerExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler([NotFoundException])
  HttpEntity notFound(NotFoundException notFoundException, WebRequest request) {
    new HttpEntity([status: 404, message: notFoundException.message])
  }
}