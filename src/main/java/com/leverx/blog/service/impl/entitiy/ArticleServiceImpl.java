package com.leverx.blog.service.impl.entitiy;

import com.leverx.blog.dto.ArticleDto;
import com.leverx.blog.dto.UserDto;
import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Role;
import com.leverx.blog.entity.enums.ArticleStatus;
import com.leverx.blog.exception.NotFoundEntityException;
import com.leverx.blog.mapper.ArticleMapper;
import com.leverx.blog.repository.ArticleRepository;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.TagService;
import com.leverx.blog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final TagService tagService;
    private final UserService userService;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleDto save(ArticleDto articleDto, String authorEmail) {
        Article article = articleMapper.mapToEntity(articleDto);
        article.setAuthorId(userService.findIdByEmail(authorEmail));
        article.setCreatedAt(new Date());
        article.setStatus(ArticleStatus.PUBLIC);

        tagService.initTagsIfNotExist(article.getTags());

        return articleMapper.mapToDto(articleRepository.saveAndFlush(article));
    }

    @Override
    public void updateById(ArticleDto articleDto, int id, String editorEmail) {
        Article article = articleRepository.findById(id).orElseThrow(()->{
            throw new NotFoundEntityException(Article.class, id);
        });
        throwIfDoesntBelong(editorEmail, article);
        updateArticle(articleDto, article);

        tagService.initTagsIfNotExist(articleDto.getTags());

        articleRepository.save(article);
    }

    private void throwIfDoesntBelong(String editorEmail, Article article) {
        UserDto editor = userService.findByEmail(editorEmail);
        if (article.getAuthorId() != editor.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }

    private void updateArticle(ArticleDto articleDto, Article article) {
        article.setText(articleDto.getText());
        article.setTitle(articleDto.getTitle());
        article.setTags(articleDto.getTags());
        article.setUpdatedAt(new Date());
    }

    @Override
    public List<ArticleDto> findArticlesByEmail(String email) {
        return fetchLazyTagsFromList(articleRepository.findArticlesByAuthorId(userService.findIdByEmail(email)))
                .stream()
                .map(articleMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(int id, String userEmail) {
        assertArticleExistsAndUserHasAccess(userEmail, id);
        articleRepository.deleteById(id);
    }

    @Override
    public List<ArticleDto> findAllByAuthorAndPageable(Integer authorId, Pageable pageable) {
        if (authorId != null) {
            return fetchLazyTagsFromList(articleRepository.findAllByAuthorId(authorId, pageable)).stream()
                    .map(articleMapper::mapToDto)
                    .collect(Collectors.toList());
        }
        return fetchLazyTagsFromPage(articleRepository.findAll(pageable)).stream()
                .map(articleMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> findAllByTagNames(String[] tagNames) {
        return articleRepository.findDistinctByTags_NameIn(Set.of(tagNames)).stream()
                .distinct() // TODO: maybe delete?
                .map(articleMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private List<Article> fetchLazyTagsFromList(List<Article> articles) {
        articles.forEach(article -> article.getTags().size());
        return articles;
    }

    private Page<Article> fetchLazyTagsFromPage(Page<Article> articles) {
        articles.forEach(article -> article.getTags().size());
        return articles;
    }

    private void assertArticleExistsAndUserHasAccess(String email, int articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> {
            throw new NotFoundEntityException(Article.class, articleId);
        });
        throwIfNotAdminOrAuthor(email, article);
    }

    private void throwIfNotAdminOrAuthor(String email, Article article) {
        UserDto user = userService.findByEmail(email);
        if (!user.getRoles().contains(Role.ADMIN_ROLE) && article.getAuthorId() != user.getId()) {
            throw new AccessDeniedException("This post doesn't belong to you!");
        }
    }
}
