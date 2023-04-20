/*
Copyright [2023] [Halsyon]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.domain.converter.security.controller

import com.domain.converter.url.service.UrlService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @description Когда сайт отправляет ссылку с кодом в ответ нужно вернуть ассоциированный адрес и статус 302.
 * @author Halsyon
 */
@RequestMapping("/redirect")
@RestController
class RedirectController(
    val urlService: UrlService
) {
    private val logger = LoggerFactory.getLogger(RedirectController::class.java)

    /**
     * вернуть ассоциированный адрес и статус HttpStatus.FOUND
     * @param urlId urlId
     * @return if exist HttpStatus.TEMPORARY_REDIRECT
     */
    @GetMapping("/{urlId}")
    fun getUrl(@PathVariable urlId: String?): ResponseEntity<String> =
        ResponseEntity<String>(urlService.findById(urlId).addressUrl, HttpStatus.FOUND)
}