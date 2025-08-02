package com.wandr.kotlinjwt.config

import com.wandr.kotlinjwt.services.TokenService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
   private val userDetailsService: UserDetailsService,
   private val tokenService: TokenService
): OncePerRequestFilter() {
   override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
      val authHeader = request.getHeader("Authorization")

      if(authHeader.doesNotContainBearerToken()) {
         filterChain.doFilter(request, response)
         return
      }

      val jwtToken = authHeader!!.extractTokenValue()
      val email = tokenService.extractEmail(jwtToken)

      if (email != null && SecurityContextHolder.getContext().authentication == null) {
         val userDetails = userDetailsService.loadUserByUsername(email)
         if (tokenService.isValid(jwtToken, userDetails)) {
            updateContext(userDetails, request)
         }

         filterChain.doFilter(request, response)
      }
   }

   private fun updateContext(userDetails: UserDetails, request: HttpServletRequest) {
      val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

      authToken.details == WebAuthenticationDetailsSource().buildDetails(request)

      SecurityContextHolder.getContext().authentication = authToken
   }

   private fun String?.doesNotContainBearerToken(): Boolean {
      return this == null || !this.startsWith("Bearer ")
   }

   private fun String.extractTokenValue(): String {
      return this.substringAfter("Bearer ") // Remove "Bearer " prefix
   }
}