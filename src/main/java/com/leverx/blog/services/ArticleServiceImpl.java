package com.leverx.blog.services;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.dto.mapping.ArticleMapping;
import com.leverx.blog.entities.Article;
import com.leverx.blog.entities.Role;
import com.leverx.blog.entities.User;
import com.leverx.blog.entities.enums.ArticleStatus;
import com.leverx.blog.entities.enums.Order;
import com.leverx.blog.exceptions.NotFoundException;
import com.leverx.blog.repositories.ArticleRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.userService = userService;
    }

    @Override
    public void save(ArticleDto articleDto) {
        Article article = ArticleMapping.mapToEntity(articleDto);
        article.setAuthorId(getUserIdByEmail(articleDto.getAuthorEmail()));
        article.setCreatedAt(new Date());
        article.setStatus(ArticleStatus.PUBLIC);
        articleRepository.save(article);
    }

    @Override
    public void updateById(ArticleDto articleDto, int id) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            throw new NotFoundException(String.format("There is no article with id %d", id));
        }
        Article article = articleOpt.get();
        User editor = userService.findByEmail(articleDto.getAuthorEmail());
        if (!editor.getRoles().contains(Role.ADMIN_ROLE) && article.getAuthorId() != editor.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        article.setUpdatedAt(new Date());
        articleRepository.save(article);
    }

    @Override
    public List<Article> findArticlesByEmail(String email) {
        return articleRepository.findArticlesByAuthorId(getUserIdByEmail(email));
    }

    @Override
    public void deleteById(int id, String userEmail) {
        assertArticleExistsAndUserHasAccess(userEmail, id);
        articleRepository.deleteById(id);
    }

    @Override
    public List<Article> findAllByPaginationDto(ArticlePaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(
                paginationDto.getSkip(),
                paginationDto.getLimit(),
                Sort.by(paginationDto.getSortField().name()));
        List<Article> articles = articleRepository.findAllByAuthorId(paginationDto.getAuthorId(), pageable);
        if (paginationDto.getOrder().equals(Order.desc)) {
            Collections.reverse(articles);
        }
        return articles;
    }

    private int getUserIdByEmail(String email) {
        return userService.findByEmail(email).getId();
    }

    private void assertArticleExistsAndUserHasAccess(String email, int articleId) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isEmpty()) {
            throw new NotFoundException(String.format("No article with id %d", articleId));
        }
        User user = userService.findByEmail(email);
        if (!user.getRoles().contains(Role.ADMIN_ROLE) && articleOpt.get().getAuthorId() != user.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }
}
