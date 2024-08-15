package com.espn.crick.info.media;

import com.espn.crick.info.media.Article;
import com.espn.crick.info.media.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    public Iterable<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Optional<Article> updateArticle(Long id, Article updatedArticle) {
        return articleRepository.findById(id).map(article -> {
            article.setTitle(updatedArticle.getTitle());
            article.setBody(updatedArticle.getBody());
            article.setAuthorId(updatedArticle.getAuthorId());
            article.setTags(updatedArticle.getTags());
            return articleRepository.save(article);
        });
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
