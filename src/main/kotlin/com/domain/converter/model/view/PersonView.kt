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
package com.domain.converter.model.view

/**
 * View model
 */
class PersonView(

    val id: Long = 0,
    val userName: String? = null,
    val password: String? = null,
    val status: Boolean = false,
    var urls: MutableList<UrlView> = mutableListOf()

) {
    fun addUrl(url: UrlView) {
        if (urls == null) urls = mutableListOf()
        urls.add(url)
        url.addPerson(this)
    }

    fun removeUrl(url: UrlView) {
        urls.remove(url)
        url.person = null
    }
}
