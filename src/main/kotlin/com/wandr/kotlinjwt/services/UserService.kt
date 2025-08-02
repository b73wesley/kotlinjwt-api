package com.wandr.kotlinjwt.services

import com.wandr.kotlinjwt.repositories.UserRepository
import com.wandr.kotlinjwt.models.User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService (
   private val userRepository: UserRepository
){

   fun createUser(user: User): User? {
      val found = userRepository.findByEmail(user.email)

      return if (found == null) {
         userRepository.save(user)
         user
      } else {
         null // User with this email already exists
      }
   }

   fun findUserByUUID(id: UUID): User? {
      return userRepository.findById(id)
   }

   fun findByAll(): List<User> {
      return userRepository.findAll()
   }

   fun deleteUserByUUID(id: UUID): Boolean {
      return userRepository.deleteById(id)
   }

}