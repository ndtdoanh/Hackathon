package com.hacof.analytics.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hacof.analytics.constant.BlogPostStatus;
import com.hacof.analytics.dto.request.BlogPostRequest;
import com.hacof.analytics.dto.response.BlogPostResponse;
import com.hacof.analytics.entity.BlogPost;
import com.hacof.analytics.exception.AppException;
import com.hacof.analytics.exception.ErrorCode;
import com.hacof.analytics.mapper.BlogPostMapper;
import com.hacof.analytics.repository.BlogPostRepository;
import com.hacof.analytics.service.BlogPostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BlogPostServiceImpl implements BlogPostService {
    BlogPostRepository blogPostRepository;
    BlogPostMapper blogPostMapper;

    @Override
    public BlogPostResponse createBlogPost(BlogPostRequest request) {
        if (blogPostRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BLOG_SLUG_ALREADY_EXISTS);
        }

        BlogPost blogPost = blogPostMapper.toEntity(request);

        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public List<BlogPostResponse> getBlogPosts() {
        return blogPostRepository.findAll().stream()
                .map(blogPostMapper::toResponse)
                .toList();
    }

    @Override
    public BlogPostResponse getBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        return blogPostMapper.toResponse(blogPost);
    }

    @Override
    public List<BlogPostResponse> getPublishedBlogPosts() {
        return blogPostRepository.findByStatus(BlogPostStatus.PUBLISHED).stream()
                .map(blogPostMapper::toResponse)
                .toList();
    }

    @Override
    public BlogPostResponse submitBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        if (blogPost.getStatus() != BlogPostStatus.DRAFT) {
            throw new AppException(ErrorCode.BLOG_CANNOT_SUBMIT);
        }
        blogPost.setStatus(BlogPostStatus.PENDING_REVIEW);
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse approveBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        if (blogPost.getStatus() != BlogPostStatus.PENDING_REVIEW) {
            throw new AppException(ErrorCode.BLOG_CANNOT_APPROVE);
        }
        blogPost.setStatus(BlogPostStatus.APPROVED);
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse publishBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        if (blogPost.getStatus() != BlogPostStatus.APPROVED) {
            throw new AppException(ErrorCode.BLOG_CANNOT_PUBLISH);
        }
        blogPost.setStatus(BlogPostStatus.PUBLISHED);
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse unpublishBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        if (blogPost.getStatus() != BlogPostStatus.PUBLISHED) {
            throw new AppException(ErrorCode.BLOG_CANNOT_UNPUBLISH);
        }
        blogPost.setStatus(BlogPostStatus.APPROVED);
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse getBlogPostBySlug(String slug) {
        BlogPost blogPost =
                blogPostRepository.findBySlug(slug).orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
        return blogPostMapper.toResponse(blogPost);
    }

    @Override
    public BlogPostResponse rejectBlogPost(Long id) {
        BlogPost blogPost = findBlogPostById(id);
        if (blogPost.getStatus() != BlogPostStatus.PENDING_REVIEW) {
            throw new AppException(ErrorCode.BLOG_CANNOT_REJECT);
        }
        blogPost.setStatus(BlogPostStatus.REJECTED);
        return blogPostMapper.toResponse(blogPostRepository.save(blogPost));
    }

    @Override
    public BlogPostResponse updateBlogPost(Long id, BlogPostRequest request) {
        BlogPost existing = findBlogPostById(id);

        if (!existing.getSlug().equals(request.getSlug()) && blogPostRepository.existsBySlug(request.getSlug())) {
            throw new AppException(ErrorCode.BLOG_SLUG_ALREADY_EXISTS);
        }

        existing.setTitle(request.getTitle());
        existing.setContent(request.getContent());
        existing.setSlug(request.getSlug());
        existing.setBannerImageUrl(request.getBannerImageUrl());
        if (request.getStatus() != null) {
            existing.setStatus(request.getStatus());
        }
        return blogPostMapper.toResponse(blogPostRepository.save(existing));
    }

    @Override
    public void deleteBlogPost(Long id) {
        if (!blogPostRepository.existsById(id)) {
            throw new AppException(ErrorCode.BLOG_NOT_FOUND);
        }
        blogPostRepository.deleteById(id);
    }

    private BlogPost findBlogPostById(Long id) {
        return blogPostRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.BLOG_NOT_FOUND));
    }
}
