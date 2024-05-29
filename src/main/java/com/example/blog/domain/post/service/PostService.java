package com.example.blog.domain.post.service;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public List<Post> getList() {
        return postRepository.findAll();
    }

    public void create(String title, String content, MultipartFile thumbnail) {
        // UUID가 자동으로 이름 부여(난수로 작성해줌)
        String thumbnailRelPath = "post/" + UUID.randomUUID().toString() + ".jpg";
        // 경로 저장을 위한 경로 설정
        File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

        try{
            thumbnail.transferTo(thumbnailFile);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        Post post = Post.builder()
                .title(title)
                .content(content)
                .thumbnailImg(thumbnailRelPath)
                .createDate(LocalDateTime.now())
                .build();
        postRepository.save(post);
    }

    public Post getPost(Long id) {
        Optional<Post> op = postRepository.findById(id);
        if ( op.isPresent() == false ) throw new DateTimeException("post not found");

        return op.get();
    }
}
