package com.domain.converter.person.service

import com.domain.converter.person.model.model.Person
import com.domain.converter.url.model.entity.Url
import com.domain.converter.person.model.view.PersonView
import com.domain.converter.person.repository.PersonRepository
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
                personRepository.findByUserName(it)
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

//    fun addPersonToRegistrationStatus(person: Person): RegstrnStatus? {
//        val regstrnStatus = RegstrnStatus()
//        regstrnStatus.setRegistration(person.isStatus())
//        regstrnStatus.setLogin(person.getUsername())
//        regstrnStatus.setPassword(person.getPassword())
//        return regstrnStatus
//    }

    private fun getEntityById(entityId: Long?): Person =
        entityId
            ?.let {
                personRepository.findById(it)
                    .orElseThrow { RuntimeException("Не существует с таким id") }
            } ?: throw RuntimeException("Person Id must not be null!")
}