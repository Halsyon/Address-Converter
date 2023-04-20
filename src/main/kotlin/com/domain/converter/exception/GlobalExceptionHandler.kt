package com.domain.converter.exception

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * value = { IllegalArgumentException.class } указывает, что обработчик будет обрабатывать
 * только данное исключение. Можно перечислить их больше, т.к. value это массив.
 */
class GlobalExceptionHandler(val objectMapper: ObjectMapper) {

    val LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler::class.java.simpleName)

    @ExceptionHandler(value = [NullPointerException::class])
    @Throws(IOException::class)
    fun handleException(e: Exception, request: HttpServletRequest?, response: HttpServletResponse) {
        response.status = HttpStatus.BAD_REQUEST.value()
        response.contentType = "application/json"
        response.writer.write(objectMapper!!.writeValueAsString(object : HashMap<Any?, Any?>() {
            init {
                put("message", "Some of fields empty")
                put("details", e.message)
            }
        }))
        LOGGER.error(e.message)
    }
}