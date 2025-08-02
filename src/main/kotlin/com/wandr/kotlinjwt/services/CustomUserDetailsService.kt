package com.wandr.kotlinjwt.services

import com.wandr.kotlinjwt.repositories.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.wandr.kotlinjwt.models.User

@Service
class CustomUserDetailsService (
   private val userRepository: UserRepository
): UserDetailsService{

   override fun loadUserByUsername(username: String): UserDetails = userRepository.findByEmail(username)
      ?.mapToUserDetails()
      ?: throw UsernameNotFoundException("User with email $username not found")

   private fun ApplicationUser.mapToUserDetails(): UserDetails = User.builder()
      .username(this.email)
      .password(this.password)
      .roles(this.role.name)
      .build()

}