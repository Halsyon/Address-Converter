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
package com.domain.converter.person.service

import com.domain.converter.person.model.model.Person
import com.domain.converter.url.model.entity.Url
import com.domain.converter.person.model.view.PersonView
import com.domain.converter.person.repository.PersonRepository
import com.domain.converter.security.model.RegistrationStatus
import org.modelmapper.ModelMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

import kotlin.RuntimeException

/**
 * @description -
 */
@Service
class PersonService(
    val personRepository: PersonRepository,
    val modelMapper: ModelMapper
) {

    fun findAll(pageable: Pageable): Page<Person> {
        return personRepository.findAll(pageable)
    }

    fun findById(personId: Long?): PersonView =
        personId.let {
            val person = getEntityById(it)
            modelMapper.map(person, PersonView::class.java)
        }

    fun delete(personId: Long?) {
        val personResult = getEntityById(personId)
        personRepository.delete(personResult)
    }

    fun findByName(userName: String?): Person =
        userName
            ?.let {
                personRepository.findPersonByUserName(it)
                    .orElseThrow { RuntimeException("Не существует с таким user name!") }
            } ?: throw RuntimeException("Person user name must not be null!")

    fun createPerson(person: Person?): PersonView {
        var result: Person = Person()
        person
            ?.let { result = personRepository.save(person) }
            ?: throw RuntimeException("some")

        return modelMapper.map(result, PersonView::class.java)
    }

    fun addUrl(userName: String?, url: Url?): Person? {
        val person = findByName(userName)
        url
            ?.let {
                person.addUrl(url)
            } ?: throw RuntimeException("id")

        return personRepository.save(person)
    }

    fun addPersonToRegistrationStatus(person: Person?): RegistrationStatus? {
        person?.let {
            val registrantStatus = RegistrationStatus()
            registrantStatus.registration = person.status
            registrantStatus.login = person.userName
            registrantStatus.password = person.password
            return registrantStatus
        } ?:throw RuntimeException("Person must be not null")

    }

    private fun getEntityById(entityId: Long?): Person =
        entityId
            ?.let {
                personRepository.findById(it)
                    .orElseThrow { RuntimeException("Не существует с таким id") }
            } ?: throw RuntimeException("Person Id must not be null!")
}