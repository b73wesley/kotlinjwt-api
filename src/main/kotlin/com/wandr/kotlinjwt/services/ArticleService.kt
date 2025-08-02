package com.wandr.kotlinjwt.services

import com.wandr.kotlinjwt.models.Article
import com.wandr.kotlinjwt.repositories.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService (
   private val articleRepository: ArticleRepository
){
   fun findAll(): List<Article> {
      return articleRepository.findAll()
   }
}