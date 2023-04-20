package com.domain.converter.url.model.view

/**
 * Объект DTO для предоставления статистки по колличество запросов по определенной строке
 */
class UrlStatistics(
    var url: String? = null,
    val total: Int? = null
) {
    constructor(): this(null, null)
}