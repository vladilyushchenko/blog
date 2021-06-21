package com.leverx.blog.entities;

import com.leverx.blog.entities.enums.ArticleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

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
    @Type(type = "com.leverx.blog.entities.enums.EnumTypePostgreSql")
    private ArticleStatus status;

    @Column(name = "author_id")
    private int authorId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
