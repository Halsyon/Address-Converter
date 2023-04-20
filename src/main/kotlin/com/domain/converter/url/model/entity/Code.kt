package com.domain.converter.url.model.entity

data class Code(var uniqueLink: String? = null) {

    companion object {
        fun codeOf(uniqueLink: String? = null): Code {
            val code = Code(uniqueLink)
            return code
        }
    }
}
