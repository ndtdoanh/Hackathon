package com.hacof.submission.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.hacof.submission.constant.BlogPostStatus;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "blog_posts")
public class BlogPost extends AuditCreatedBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "title")
    String title;

    @Column(name = "slug", unique = true)
    String slug;

    @Column(name = "banner_image_url")
    String bannerImageUrl;

    @Lob
    @Column(name = "content")
    String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    BlogPostStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewed_by")
    User reviewedBy;

    @Column(name = "published_at")
    LocalDateTime publishedAt;
}
