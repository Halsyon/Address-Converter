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
package com.domain.converter.url.model.entity

import com.domain.converter.person.model.model.Person
import javax.persistence.*

/**
 * @description-URL После того, как пользователь зарегистрировал свой сайт,
 * он может отправлять на сайт ссылки и получать преобразованные ссылки.
 * @author Halsyon
 */
@Entity(name = "url")
data class Url(

    @Id @Column(name = "encode_url")
    var encodeUrl: String? = null,

    @Column(name = "address_url")
    var addressUrl: String? = null,

    @Version
    @Column(name = "total")
    var total: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    var person: Person?
) {
    constructor(): this(null, null, null, null)

    fun addPerson(person: Person) {
        this.person = person
    }

    fun removePerson() {
        this.person = null
    }
}
