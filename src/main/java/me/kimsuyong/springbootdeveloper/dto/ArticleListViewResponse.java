package me.kimsuyong.springbootdeveloper.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import me.kimsuyong.springbootdeveloper.domain.Article;

@Getter
public class ArticleListViewResponse {
	private final Long id;
	private final String title;
	private final String content;
	private final LocalDateTime createdAt;

	public ArticleListViewResponse(Article article) {
		this.id = article.getId();
		this.title = article.getTitle();
		this.content = article.getTitle();
		this.createdAt = getCreatedAt();
	}
}
