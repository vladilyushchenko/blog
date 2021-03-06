package com.leverx.blog.entity;

import com.leverx.blog.entity.enums.ArticleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@Table(name = "articles")
@Entity
@NoArgsConstructor
public class Article {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "status", columnDefinition = "article_status")
    @Enumerated(EnumType.STRING)
    @Type(type = "com.leverx.blog.entity.enums.EnumTypePostgreSql")
    private ArticleStatus status;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tags_articles",
                joinColumns = @JoinColumn(name = "article_id"),
                inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
