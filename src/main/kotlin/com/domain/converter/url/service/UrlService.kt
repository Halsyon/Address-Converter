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
package com.domain.converter.url.service

import com.domain.converter.person.service.PersonService
import com.domain.converter.url.model.entity.Code
import com.domain.converter.url.model.entity.Link
import com.domain.converter.url.model.entity.Url
import com.domain.converter.url.model.view.UrlStatistics
import com.domain.converter.url.model.view.UrlView
import com.domain.converter.url.repository.UrlRepository
import com.google.common.hash.Hashing
import org.apache.commons.validator.routines.UrlValidator
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.util.stream.StreamSupport


/**
 * @description
 * @author Halsyon
 */
@Service
class UrlService(
    val urlRepository: UrlRepository,
    val personService: PersonService,
    val modelMapper: ModelMapper
) {

    //todo access
    fun findAll(pageable: Pageable): Page<Url> {
        return urlRepository.findAll(pageable)
    }

    fun createUrl(url: Url?): Url =
        url
            ?.let {
                urlRepository.save(it)
            } ?: throw RuntimeException("Please enter a valid url link !")


    fun convertAndSaveUrlLink(link: Link?): ResponseEntity<Code> {
        if (link != null) {
            if (link.url == null) {
                throw NullPointerException("URL id mustn't be empty")
            }
        }
        val holder: Any = SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal()
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        val url: String? = link?.url
        if (urlValidator.isValid(url)) {
            val id = encodeLink(url)
            val url1 = putAddressAndUrl(url, id)?.let { createUrl(it) }
            personService.addUrl(holder.toString(), url1)
            return ResponseEntity<Code>(Code.codeOf(id), HttpStatus.OK)
        }
        return ResponseEntity<Code>(
            Code.codeOf("Please enter a valid address url link !"),
            HttpStatus.BAD_REQUEST
        )
    }

    /**
     * найти ссылку по ключу
     */
    fun findById(urlId: String?): UrlView =
        urlId
            ?.let {
            val url = getEntityById(it)
            modelMapper.map(url, UrlView::class.java)
        } ?: throw RuntimeException("Please enter a valid id url link !")

    /**
     * шифрование ссылки
     */
    fun encodeLink(url: String?): String? {
        var id: String? = null
        val urlValidator = UrlValidator(arrayOf("http", "https"))
        if (urlValidator.isValid(url)) {
            id = Hashing.murmur3_32().hashString(url!!, StandardCharsets.UTF_8).toString()
        }
        return id
    }

    fun putAddressAndUrl(url: String?, urlId: String?): Url? {
        var urlResult = Url()
        urlResult.addressUrl = url
        urlResult.encodeUrl = urlId
        return urlRepository.save(urlResult)
    }


    /**
     * можно получить статистку всех адресов и количество вызовов этого адреса.
     * Page<Something> page = new PageImpl<>(yourList);
     */
    fun findAllStatistic(): Page<UrlStatistics> {
        val listStatistic = urlRepository.findAll()
            .map { url ->
                UrlStatistics(
                    url.addressUrl,
                    url.total
                )
            }
            .toList()
        return PageImpl(listStatistic)
    }

    fun delete(urlId: String?) {
        val urlResult = getEntityById(urlId)
        urlRepository.delete(urlResult)
    }

//
//    fun findByName(userName: String?): Person =
//        userName
//            ?.let {
//                personRepository.findByUserName(it)
//                    .orElseThrow { RuntimeException("Не существует с таким user name!") }
//            } ?: throw RuntimeException("Person user name must not be null!")


//
//    fun addUrl(userName: String?, url: Url?): Person? {
//        val person = findByName(userName)
//        url
//            ?.let {
//                person.addUrl(url)
//            } ?: throw RuntimeException("id")
//
//        return personRepository.save(person)
//    }

    //
////    fun addPersonToRegistrationStatus(person: Person): RegstrnStatus? {
////        val regstrnStatus = RegstrnStatus()
////        regstrnStatus.setRegistration(person.isStatus())
////        regstrnStatus.setLogin(person.getUsername())
////        regstrnStatus.setPassword(person.getPassword())
////        return regstrnStatus
////    }
//
    private fun getEntityById(urlId: String?): Url =
        urlId
            ?.let {
                urlRepository.findById(it)
                    .orElseThrow { RuntimeException("Не существует с таким id") }
            } ?: throw RuntimeException("Url Id must not be null!")
}