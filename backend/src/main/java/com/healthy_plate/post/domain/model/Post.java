package com.healthy_plate.post.domain.model;

import com.healthy_plate.shared.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "writer_id")
    private Long writerId;

    @Column(length = 200, name = "title")
    private String title;

    @Column(length = 150, name = "description")
    private String description;

    @Column(name = "content")
    private String content;

    @Column(name = "thumbnail_image_url")
    private String thumbnailImageUrl;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
