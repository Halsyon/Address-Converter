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
package com.domain.converter.person.model.model

import com.domain.converter.url.model.entity.Url
import javax.persistence.*
import javax.validation.constraints.NotBlank

/**
 * @description Main model
 * @author Halsyon
 */
@Entity(name = "person")
data class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "person_id", nullable = false)
    var id: Long = 0,

    @NotBlank(message = "username must be not empty")
    @Column(name = "username")
    var userName: String? = null,

    @NotBlank(message = "password must be not empty")
    @Column(name = "password")
    var password: String? = null,

    @Column(name = "status")
    var status: Boolean = false,

    @Column(nullable = true)
    @OneToMany(orphanRemoval = true, cascade = [CascadeType.ALL], mappedBy = "person")
    var urls: MutableList<Url> = mutableListOf()

) {

    fun addUrl(url: Url) {
        urls.add(url)
        url.addPerson(this)
    }

    fun removeUrl(url: Url) {
        urls.remove(url)
        url.person = null
    }
}
