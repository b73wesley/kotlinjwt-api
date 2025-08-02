package com.wandr.kotlinjwt.controllers.auth

data class AuthRequest(
   val email: String,
   val password: String,
)
