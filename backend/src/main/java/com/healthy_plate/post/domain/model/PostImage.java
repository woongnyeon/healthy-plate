package com.healthy_plate.post.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_image")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "draft_id")
    private Draft draft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "s3_key")
    private String s3Key;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "file_size")
    private Long fileSize;

    private PostImage(final String s3Key, final String originalName, final Long fileSize) {
        this.s3Key = s3Key;
        this.originalName = originalName;
        this.fileSize = fileSize;
    }

    public static PostImage create(final String s3Key, final String originalName, final Long fileSize) {
        return new PostImage(s3Key, originalName, fileSize);
    }

    public void assignToDraft(final Draft draft) {
        this.draft = draft;
        this.post = null;
    }

    public void assignToPost(final Post post) {
        this.draft = null;
        this.post = post;
    }
}