package com.wandr.kotlinjwt.controllers.auth

import com.wandr.kotlinjwt.services.auth.AuthService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
class AuthController (
   private val authService: AuthService
){
   @PostMapping
   fun authenticate(@RequestBody authRequest: AuthRequest): AuthResponse = authService.authenticate(authRequest)

   @PostMapping("/refresh")
   fun refreshToken(@RequestBody refreshTokenRequest: RefreshTokenRequest): TokenResponse =
      authService.refreshToken(refreshTokenRequest.token)?.mapToTokenResponse()?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token")

   private fun String.mapToTokenResponse(): TokenResponse {
      return TokenResponse(
         accessToken = this,
      )
   }
}