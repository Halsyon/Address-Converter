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

import com.domain.converter.person.model.model.Person
import com.domain.converter.person.service.PersonService
import com.domain.converter.security.model.RegistrationStatus
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @description Registration new Person/user
 * @author Halsyon
 */
@RestController
@RequestMapping("/registration")
class RegistrationController(
    val personService: PersonService,
    var encoder: BCryptPasswordEncoder
) {

    /**
     * Регистрация в системе
     * registration : true/false
     * Флаг registration указывает, что регистрация выполнена или нет, то есть сайт уже есть в системе.
     *
     * @param person
     * @return
     */
    @PostMapping
    fun registration(@RequestBody person: @Valid Person?): ResponseEntity<RegistrationStatus> {
        val registrationStatus =  personService.addPersonToRegistrationStatus(person)

        if (person?.userName.equals(null) || person?.password.equals(null)) {
            throw NullPointerException("Person username and password mustn't be empty!")
        }
        val userUnique = personService.findByName(person?.userName)
        person?.password = encoder.encode(person?.password)
        personService.createPerson(person)
        if (registrationStatus != null) {
            registrationStatus.registration = true
        }
        return ResponseEntity<RegistrationStatus>(
            registrationStatus,
            HttpStatus.CREATED
        )
    }
}