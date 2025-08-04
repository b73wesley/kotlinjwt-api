package com.wandr.kotlinjwt.services.auth

import com.wandr.kotlinjwt.config.JwtProperties
import com.wandr.kotlinjwt.controllers.auth.AuthRequest
import com.wandr.kotlinjwt.controllers.auth.AuthResponse
import com.wandr.kotlinjwt.repositories.RefreshTokenRepository
import com.wandr.kotlinjwt.services.CustomUserDetailsService
import com.wandr.kotlinjwt.services.TokenService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthService (
   private val authManaged: AuthenticationManager,
   private val userDetailsService: CustomUserDetailsService,
   private val tokenService: TokenService,
   private val jwtProperties: JwtProperties,
   private val refreshTokenRepository: RefreshTokenRepository,
){

   fun authenticate(authRequest: AuthRequest): AuthResponse {
      authManaged.authenticate(
         UsernamePasswordAuthenticationToken(
            authRequest.email,
            authRequest.password
         )
      )

      val user = userDetailsService.loadUserByUsername(authRequest.email)
      val accessToken = generateAccessToken(user)

      val refreshToken = tokenService.generate(userDetails = user, expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration))

      refreshTokenRepository.save(refreshToken, user)

      return AuthResponse(accessToken = accessToken, refreshToken = refreshToken)
   }

   private fun generateAccessToken(user: UserDetails) =
      tokenService.generate(
         userDetails = user,
         expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
      )

   fun refreshToken(token: String): String? {
      val extractedEmail = tokenService.extractEmail(token)

      return extractedEmail.let { email ->
         val currentUserDetails = userDetailsService.loadUserByUsername(email)
         val refreshToken = refreshTokenRepository.findUserDetailsByToken(token)

         if (!tokenService.isExpired(token) && currentUserDetails.username == refreshToken?.username)
            generateAccessToken(currentUserDetails)
         else
            null
      }
   }

}
