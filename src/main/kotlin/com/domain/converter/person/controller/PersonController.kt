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
 * @author
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