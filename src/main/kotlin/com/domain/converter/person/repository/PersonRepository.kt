package com.domain.converter.person.repository

import com.domain.converter.person.model.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
/**
 * @description
 * @author
 */
interface PersonRepository: JpaRepository<Person, Long> {

    fun findPersonByUserName(userName: String): Optional<Person>
}