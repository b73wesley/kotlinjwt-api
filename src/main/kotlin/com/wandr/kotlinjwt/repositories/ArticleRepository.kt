package com.wandr.kotlinjwt.repositories

import com.wandr.kotlinjwt.models.Article
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class ArticleRepository {
   private val articles = listOf(
      Article(
         id = UUID.randomUUID(),
         title = "First Article",
         content = "This is the content of the first article.",
      ),
      Article(
         id = UUID.randomUUID(),
         title = "Second Article",
         content = "This is the content of the second article.",
      ),
      Article(
         id = UUID.randomUUID(),
         title = "Third Article",
         content = "This is the content of the third article.",
      ),
   )

   fun findAll(): List<Article> = articles
}