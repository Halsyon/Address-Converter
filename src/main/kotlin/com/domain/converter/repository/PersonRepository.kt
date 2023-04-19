package com.domain.converter.repository

import com.domain.converter.model.model.Person
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PersonRepository: JpaRepository<Person, Long> {

    fun findByUserName(userName: String): Optional<Person>
}