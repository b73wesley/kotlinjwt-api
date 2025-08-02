package com.wandr.kotlinjwt.services.auth

import com.wandr.kotlinjwt.config.JwtProperties
import com.wandr.kotlinjwt.controllers.auth.AuthRequest
import com.wandr.kotlinjwt.controllers.auth.AuthResponse
import com.wandr.kotlinjwt.services.CustomUserDetailsService
import com.wandr.kotlinjwt.services.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService (
   private val authManaged: AuthenticationManager,
   private val userDetailsService: CustomUserDetailsService,
   private val tokenService: TokenService,
   private val jwtProperties: JwtProperties,
){

   fun authenticate(authRequest: AuthRequest): AuthResponse {
      authManaged.authenticate(
         UsernamePasswordAuthenticationToken(
            authRequest.email,
            authRequest.password
         )
      )

      val user = userDetailsService.loadUserByUsername(authRequest.email)
      val accessToken = tokenService.generate(userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration))

      return AuthResponse(accessToken = accessToken)
   }

}
