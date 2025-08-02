package com.wandr.kotlinjwt.controllers.user

import java.util.UUID

data class UserRequest(
   val email: String,
   val password: String,
)

data class UserResponse(
   val id: UUID,
   val email: String,
   val role: String
)