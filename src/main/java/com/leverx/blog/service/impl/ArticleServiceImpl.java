package com.leverx.blog.service.impl;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.ArticlePaginationDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.dto.mapping.ArticleMapping;
import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.Tag;
import com.leverx.blog.entity.enums.ArticleStatus;
import com.leverx.blog.entity.enums.Order;
import com.leverx.blog.exception.NotFoundException;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.repository.TagRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final UserService userService;

    public ArticleServiceImpl(ArticleRepository articleRepository, TagRepository tagRepository, UserService userService) {
        this.articleRepository = articleRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
    }

    @Transactional
    @Override
    public ArticleDto save(ArticleDto articleDto, String authorEmail) {
        Article article = ArticleMapping.mapToEntity(articleDto);
        article.setAuthorId(userService.findIdByEmail(authorEmail));
        article.setCreatedAt(new Date());
        article.setStatus(ArticleStatus.PUBLIC);

        initTagsIfNotExist(article.getTags());

        return ArticleMapping.mapToDto(articleRepository.save(article));
    }

    @Override
    public void updateById(ArticleDto articleDto, int id, String editorEmail) {
        Optional<Article> articleOpt = articleRepository.findById(id);
        if (articleOpt.isEmpty()) {
            throw new NotFoundException(String.format("There is no article with id %d", id));
        }
        Article article = articleOpt.get();
        UserDto editor = userService.findByEmail(editorEmail);
        if (article.getAuthorId() != editor.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());

        initTagsIfNotExist(articleDto.getTags());

        article.setTags(articleDto.getTags());
        article.setUpdatedAt(new Date());
        articleRepository.save(article);
    }

    @Override
    public List<ArticleDto> findArticlesByEmail(String email) {
        return articleRepository
                .findArticlesByAuthorId(userService.findIdByEmail(email))
                .stream()
                .map(ArticleMapping::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id, String userEmail) {
        assertArticleExistsAndUserHasAccess(userEmail, id);
        articleRepository.deleteById(id);
    }

    @Override
    public List<ArticleDto> findAllByPaginationDto(ArticlePaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(
                paginationDto.getSkip(),
                paginationDto.getLimit(),
                Sort.by(paginationDto.getSortField().name()));
        List<Article> articles = articleRepository.findAllByAuthorId(paginationDto.getAuthorId(), pageable);
        if (paginationDto.getOrder().equals(Order.desc)) {
            Collections.reverse(articles);
        }
        return articles.stream().map(ArticleMapping::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllByTagNames(String[] tagNames) {
        return articleRepository.findDistinctByTags_NameIn(Set.of(tagNames))
                .stream()
                .distinct()
                .map(ArticleMapping::mapToDto)
                .collect(Collectors.toList());
    }

    private void initTagsIfNotExist(Set<Tag> tags) {
        if (tags != null) {
            for (Tag tag : tags) {
                Optional<Tag> tagEntity = tagRepository.findByName(tag.getName());
                tagEntity.ifPresentOrElse(
                        value -> tag.setId(value.getId()),
                        () -> tag.setId(tagRepository.save(tag).getId())
                );
            }
        }
    }

    private void assertArticleExistsAndUserHasAccess(String email, int articleId) {
        Optional<Article> articleOpt = articleRepository.findById(articleId);
        if (articleOpt.isEmpty()) {
            throw new NotFoundException(String.format("No article with id %d", articleId));
        }
        UserDto user = userService.findByEmail(email);
        if (!user.getRoles().contains(Role.ADMIN_ROLE) && articleOpt.get().getAuthorId() != user.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }
}
