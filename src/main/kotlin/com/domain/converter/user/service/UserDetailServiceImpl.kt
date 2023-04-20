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
package com.domain.converter.user.service

import com.domain.converter.person.model.model.Person
import com.domain.converter.person.repository.PersonRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

/**
 * @author Halsyon
 */
@Service
class UserDetailServiceImpl(
    val personRepository: PersonRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user: Person =
            personRepository.findPersonByUserName(username)
                .orElseThrow { throw UsernameNotFoundException(username) }
        return User(user.userName, user.password, emptyList())
    }
}