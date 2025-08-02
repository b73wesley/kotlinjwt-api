package com.wandr.kotlinjwt.services

import com.wandr.kotlinjwt.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class TokenService (
   jwtProperties: JwtProperties
){
   private val secretKey = Keys.hmacShaKeyFor(
      jwtProperties.key.toByteArray()
   )

   fun generate(
      userDetails: UserDetails,
      expirationDate: Date,
      additinalClaims: Map<String, Any> = emptyMap()
   ): String = Jwts.builder()
      .setClaims(additinalClaims)
      .setSubject(userDetails.username)
      .setIssuedAt(Date(System.currentTimeMillis()))
      .setExpiration(expirationDate)
      .signWith(secretKey)
      .compact()

   fun extractEmail(token: String): String {
      val claims = getAllClaims(token)
      return claims.subject
   }

   fun isExpired(token: String): Boolean {
      val claims = getAllClaims(token)
      return claims.expiration.before(Date(System.currentTimeMillis()))
   }

   fun isValid(token: String, userDetails: UserDetails): Boolean {
      val email = extractEmail(token)

      return userDetails.username == email && !isExpired(token)
   }

   private fun getAllClaims(token: String): Claims {
      val parser = Jwts.parserBuilder()
         .setSigningKey(secretKey)
         .build()

      return parser.parseClaimsJws(token).body
   }

}