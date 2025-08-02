package com.wandr.kotlinjwt.controllers.article

import com.wandr.kotlinjwt.models.Article
import com.wandr.kotlinjwt.services.ArticleService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/articles")
class ArticleController (
   private val service: ArticleService
) {
   @GetMapping
   fun listArticles() = service.findAll().map { it.toResponse() }

   private fun Article.toResponse() = ArticleResponse(
      id = this.id,
      title = this.title,
      content = this.content
   )
}