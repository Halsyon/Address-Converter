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
package com.domain.converter.person.controller

import com.domain.converter.person.model.model.Person
import com.domain.converter.person.model.view.PersonView
import com.domain.converter.person.service.PersonService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @description
 * @author - Halsyon
 */
@RestController
@RequestMapping("v1/link-converter/person")
class PersonController(
    val personService: PersonService
) {

    @GetMapping()
    fun findAll(pageable: Pageable): Page<Person> {
        return personService.findAll(pageable)
    }

    @GetMapping("/{personId}")
    fun findById(@PathVariable personId: Long): PersonView =
        personService.findById(personId)

    @PutMapping("/")
    fun create(@RequestBody person: @Valid Person?): PersonView {
        return personService.createPerson(person)
    }

    @PostMapping("/{personId}")
    fun update(
        @PathVariable personId: Long,
        @RequestBody person: @Valid Person?
    ): PersonView {
        return personService.createPerson(person)
    }

    @DeleteMapping("/{personId}")
    fun delete(@PathVariable personId: Long) {
        personService.delete(personId)
    }
}