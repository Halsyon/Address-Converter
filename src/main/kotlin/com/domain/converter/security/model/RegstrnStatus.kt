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
package com.domain.converter.security.model

/**
 * @description
 * Отчет о регистрации сайта DTO Object
 * Флаг registration указывает, что регистрация выполнена или нет,
 * то есть сайт уже есть в системе.
 * @author Halsyon
 */
data class RegistrationStatus(
    var registration: Boolean = false,
    var login: String? = null,
    var password: String? = null
) {
    constructor() : this(false, null, null)
}
