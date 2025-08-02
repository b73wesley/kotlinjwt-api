package com.wandr.kotlinjwt.repositories

import com.wandr.kotlinjwt.models.Role
import com.wandr.kotlinjwt.models.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserRepository(
   private val encoder: PasswordEncoder
){
   private val users = mutableListOf(
      User(
         id = UUID.randomUUID(),
         email = "email-1@gmail.com",
         password = encoder.encode("pass1"),
         role = Role.USER
      ),
      User(
         id = UUID.randomUUID(),
         email = "email-2@gmail.com",
         password = encoder.encode("pass2"),
         role = Role.ADMIN
      ),
      User(
         id = UUID.randomUUID(),
         email = "email-3@gmail.com",
         password = encoder.encode("pass3"),
         role = Role.USER
      )
   )

   fun save(user: User): Boolean {
      val updated = user.copy(password = encoder.encode(user.password))
      return users.add(updated)
   }

   fun findByEmail(email: String): User? {
      return users.firstOrNull { it.email == email }
   }

   fun findAll(): List<User> = users

   fun findById(id: UUID): User? {
      return users.firstOrNull { it.id == id }
   }

   fun deleteById(id: UUID): Boolean {
      val user = findById(id)
      return if (user != null) {
         users.remove(user)
      } else {
         false
      }
   }
}