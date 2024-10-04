package com.dpds.kokos_football_club.security

import com.dpds.kokos_football_club.user.UserRepository
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthTokenFilter @Autowired constructor(
    private val tokenProvider: JwtSigner,
    private val userDetailsService: UserDetailsServiceImpl,
    private val userRepository: UserRepository
): OncePerRequestFilter(){
    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val superRes = super.shouldNotFilter(request)
        return superRes || request.toString().contains("/basic/")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        var userId: Long? = null
        val jwt: String?
        var userDetails: AuthenticatedUser? = null
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7)
            try {
                userId = tokenProvider.getJwtSubject(jwt).toLong()
                userDetails = userDetailsService.loadUserByUsername(userRepository.findByIdOrNull(userId)?.login)
            } catch (e: ExpiredJwtException) {
                logger.debug("Время жизни токена вышло")
            } catch (e: SignatureException) {
                logger.debug("Подпись неправильная")
            }
        }
        if (userDetails != null && userId != null && SecurityContextHolder.getContext().authentication == null) {
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
                userRepository.findByIdOrNull(userId)?.login,
                null,
                userDetails.authorities
            )
        }
        filterChain.doFilter(request, response)
    }
}