package com.example.blog.domain.post.controller;

import com.example.blog.domain.post.entity.Post;
import com.example.blog.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Post> postList = postService.getList();

        model.addAttribute("postList", postList);

        return "post/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {

        Post p = postService.getPost(id);

        model.addAttribute("post", p);

        return "post/detail";
    }

    @GetMapping("/create")
    public String create() {
        return "post/create_form";
    }

    @PostMapping("/create")
    public String postCreate(@RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        postService.create(title, content);

        return "redirect:/post/list";
    }
}
