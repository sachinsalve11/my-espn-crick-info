package com.espn.crick.info.media;

import com.espn.crick.info.media.Article;
import com.espn.crick.info.media.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleService articleService;

    private Article article;

    @BeforeEach
    void setUp() {
        article = new Article();
        article.setId(1L);
        article.setTitle("Test Article");
        article.setBody("This is a test article.");
        article.setAuthorId("author123");
        article.setTags("test,article");
    }

    @Test
    void getAllArticles() {
        when(articleRepository.findAll()).thenReturn(List.of(article));

        assertAll(

                ()->assertNotNull(articleService.getAllArticles()),
                ()->assertEquals(1, ((List<Article>) articleService.getAllArticles()).size())
        );
    }

    @Test
    void getArticleById() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        assertAll(
                ()->assertTrue(articleService.getArticleById(1L).isPresent()),
                ()->assertEquals("Test Article", articleService.getArticleById(1L).get().getTitle())
        );
    }

    @Test
    void createArticle() {
        when(articleRepository.save(article)).thenReturn(article);

        Article createdArticle = articleService.createArticle(article);

        assertNotNull(createdArticle);
        assertEquals("Test Article", createdArticle.getTitle());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void updateArticle() {
        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));
        when(articleRepository.save(article)).thenReturn(article);


        Article updatedArticle = new Article();
        updatedArticle.setTitle("Updated Title");
        updatedArticle.setBody("Updated Body");
        updatedArticle.setAuthorId("author123");
        updatedArticle.setTags("updated,article");

        Optional<Article> result = articleService.updateArticle(1L, updatedArticle);
        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Body", result.get().getBody());
    }

    @Test
    void deleteArticle() {
        doNothing().when(articleRepository).deleteById(1L);
        articleService.deleteArticle(1L);
        verify(articleRepository, times(1)).deleteById(1L);
    }
}
