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
package com.domain.converter.url.controller

import com.domain.converter.person.service.PersonService
import com.domain.converter.url.model.entity.Code
import com.domain.converter.url.model.entity.Link
import com.domain.converter.url.model.view.UrlStatistics
import com.domain.converter.url.service.UrlService
import org.apache.commons.validator.routines.UrlValidator
import org.springframework.data.domain.Page

//import org.apache.commons.validator.routines.UrlValidator


import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

/**
 * @description
 * @author Halsyon
 */
@RestController
@RequestMapping("v2/link-converter")
class UrlLinkController(
    val personService: PersonService,
    val urlService: UrlService
) {

    /**
     *  можно получить статистку всех адресов и количество вызовов этого адреса.
     */
    @GetMapping("/statistics")
    fun getUrlStatistics(): Page<UrlStatistics> =
        urlService.findAllStatistic()

    /**
     * вернуть обратно по ключу String url
     * @param id url key encoded
     * @return
     */
    @GetMapping("/{urlId}")
    fun getUrl(@PathVariable urlId: String?): ResponseEntity<Link?>? {
        val urlResult = urlService.findById(urlId)
        return if (urlResult.addressUrl == null) {
            ResponseEntity<Link?>(
                Link("Please enter a valid id url link !"),
                HttpStatus.BAD_REQUEST
            )
        } else ResponseEntity<Link?>(Link(urlResult.addressUrl), HttpStatus.FOUND)
    }

    /**
     * Отправляем URL.
     * {url: "https://www.geeksforgeeks.org/kotlin-mutablesetof-method/"}
     * Получаем Ключ - ZRUfD2 ассоциирован с URL.
     * Ответ от сервера.
     * {code: УНИКАЛЬНЫЙ_КОД}
     */
    @PostMapping
    fun createConvert(@RequestBody link: Link): ResponseEntity<Code> =
        urlService.convertAndSaveUrlLink(link)
}