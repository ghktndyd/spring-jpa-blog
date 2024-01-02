package me.kimsuyong.springbootdeveloper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kimsuyong.springbootdeveloper.domain.Article;

public interface BlogRepository extends JpaRepository<Article, Long> {

}
