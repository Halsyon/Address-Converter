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
package com.domain.converter.url.model.view

import com.domain.converter.person.model.view.PersonView

class UrlView(

    var encodeUrl: String? = null,
    val addressUrl: String? = null,
    val total: Int? = null,
    var person: PersonView?
) {
    fun addPerson(person: PersonView) {
        this.person = person
    }

    fun removePerson() {
        this.person = null
    }
}

