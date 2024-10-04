package com.dpds.kokos_football_club.security

import org.springframework.http.server.ServerHttpRequest

object AuthenticationHeaderParser {
    fun parseRequest(request: ServerHttpRequest): String? {
        val authHeader = request.headers.getOrEmpty("Authorization")[0]

        return getToken(authHeader)
    }

    fun getToken(authHeader: String?): String? {
        return if (authHeader != null && authHeader.startsWith("Bearer ")) {
            authHeader.replace("Bearer ", "")
        } else null
    }
}