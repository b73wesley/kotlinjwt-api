package com.wandr.kotlinjwt.controllers.user

import com.wandr.kotlinjwt.services.UserService
import com.wandr.kotlinjwt.models.Role
import com.wandr.kotlinjwt.models.User
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.*

@RestController
@RequestMapping("/users")
class UserController (
   private val userService: UserService
){
   @PostMapping
   fun create(@RequestBody userRequest: UserRequest): UserResponse =
      userService.createUser(user = userRequest.toModel())?.toResponse()?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create a user")

   @GetMapping
   fun listAll(): List<UserResponse> =
      userService.findByAll().map { it.toResponse() }

   @GetMapping("/{id}")
   fun findById(id: UUID): UserResponse =
      userService.findUserByUUID(id)?.toResponse() ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")

   @DeleteMapping("/{id}")
   fun deleteById(id: UUID): ResponseEntity<Boolean> {
      return if (userService.deleteUserByUUID(id)) {
         ResponseEntity.noContent().build()
      } else {
         throw ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
      }
   }

   private fun UserRequest.toModel() = User(
      id = UUID.randomUUID(),
      email = this.email,
      password = this.password,
      role = Role.USER // Default role, can be changed later
   )

   private fun User.toResponse() = UserResponse(
      id = this.id,
      email = this.email,
      role = this.role.name
   )
}