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
package com.domain.converter.security.filter

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.domain.converter.apputils.SecurityConstants
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Второй фильтр будет проверять, что в заголовке запроса есть token и если его нет, то отправлять статус 403.
 * @author Halsyon
 */
class JWTAuthorizationFilter(private val authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(SecurityConstants.HEADER_STRING)
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(req, response)
            return
        }
        val authentication = getAuthentication(req)
        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.HEADER_STRING)
        if (token != null) {
            /* parse the token. */
            val user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.toByteArray()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .subject
            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, ArrayList())
            } else null
        }
        return null
    }
}