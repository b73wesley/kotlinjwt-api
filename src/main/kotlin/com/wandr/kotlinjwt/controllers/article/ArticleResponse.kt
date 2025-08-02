package com.wandr.kotlinjwt.controllers.article

import java.util.*

data class ArticleResponse(
   val id: UUID,
   val title: String,
   val content: String,
)
