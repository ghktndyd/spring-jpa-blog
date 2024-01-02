package me.kimsuyong.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kimsuyong.springbootdeveloper.domain.Article;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

	private String title;
	private String content;

	/**
	 * Dto -> Entity
 	 */
	public Article toEntity() {
		return Article.builder()
			.title(title)
			.content(content)
			.build();
	}

}
