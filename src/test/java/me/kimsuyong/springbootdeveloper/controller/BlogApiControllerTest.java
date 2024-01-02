package me.kimsuyong.springbootdeveloper.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.kimsuyong.springbootdeveloper.domain.Article;
import me.kimsuyong.springbootdeveloper.dto.AddArticleRequest;
import me.kimsuyong.springbootdeveloper.dto.ArticleResponse;
import me.kimsuyong.springbootdeveloper.dto.UpdateArticleRequest;
import me.kimsuyong.springbootdeveloper.repository.BlogRepository;

@AutoConfigureMockMvc
@SpringBootTest
class BlogApiControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper; // 직렬화, 역직렬화용 클래스

	@Autowired
	protected WebApplicationContext context;

	@Autowired
	BlogRepository blogRepository;

	@BeforeEach
	public void setMockMvc() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
			.build();
		blogRepository.deleteAll();
	}

	@DisplayName("addArticle: 블로그 글 추가에 성공한다.")
	@Test
	public void addArticle() throws Exception {
		//Given
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";
		final AddArticleRequest userRequest = new AddArticleRequest(title, content);

		// Json으로 직렬화
		final String requestBody = objectMapper.writeValueAsString(userRequest);

		//When
		ResultActions result = mockMvc.perform(post(url)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(requestBody));

		//Then
		result.andExpect(status().isCreated());

		List<Article> articles = blogRepository.findAll();

		assertThat(articles.size()).isEqualTo(1);
		assertThat(articles.get(0).getTitle()).isEqualTo(title);
		assertThat(articles.get(0).getContent()).isEqualTo(content);

	}

	@DisplayName("findAllArticles: 블로그 글 목록 조회에 성공한다.")
	@Test
	public void findAllArticles() throws Exception {
		//Given
		final String url = "/api/articles";
		final String title = "title";
		final String content = "content";

		blogRepository.save(Article.builder()
			.title(title)
			.content(content)
			.build());

		//When
		final ResultActions resultActions = mockMvc.perform(get(url)
			.accept(MediaType.APPLICATION_JSON));

		//Then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].content").value(content))
			.andExpect(jsonPath("$[0].title").value(title));
	}

	@DisplayName("findArticle: 블로그 글 조회에 성공한다.")
	@Test
	public void findArticle() throws Exception {
		//Given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(Article.builder()
			.title(title)
			.content(content)
			.build());

		//When
		ResultActions resultActions = mockMvc.perform(get(url, savedArticle.getId()));

		//Then
		resultActions
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").value(content))
			.andExpect(jsonPath("$.title").value(title));
	}

	@DisplayName("deleteArticle: 블로그 글 삭제에 성공한다.")
	@Test
	public void deleteArticle() throws Exception {
		//Given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(Article.builder()
			.title(title)
			.content(content)
			.build());

		//When
		mockMvc.perform(delete(url, savedArticle.getId()))
			.andExpect(status().isOk());

		//Then
		List<Article> articles = blogRepository.findAll();

		assertThat(articles).isEmpty();
	}

	@DisplayName("updatedArticle: 블로그 글 수정에 성공한다.")
	@Test
	public void updateArticle() throws Exception {
		//Given
		final String url = "/api/articles/{id}";
		final String title = "title";
		final String content = "content";

		Article savedArticle = blogRepository.save(Article.builder()
			.title(title)
			.content(content)
			.build());

		final String newTitle = "new Title";
		final String newContent = "new Content";

		UpdateArticleRequest request = new UpdateArticleRequest(newTitle, newContent);

		//When
		ResultActions result = mockMvc.perform(put(url, savedArticle.getId())
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(objectMapper.writeValueAsString(request)));

		//Then
		result.andExpect(status().isOk());

		Article article = blogRepository.findById(savedArticle.getId()).get();

		assertThat(article.getTitle()).isEqualTo(newTitle);
		assertThat(article.getContent()).isEqualTo(newContent);
	}
}