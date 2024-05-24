package com.example.blog.domain.post.service;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public void create(LocalDateTime createDate, String title, String content) {
        Post post = Post.builder()
                .title(title)
                .content(content)
                .createDate(createDate)
                .build();
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        Optional<Post> op = postRepository.findById(id);
        if ( op.isPresent() == false ) throw new DateTimeException("post not found");

        return op.get();
    }
}
