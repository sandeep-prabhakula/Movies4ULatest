package com.sandeepprabhakula.moviesForYou.controller;

import com.sandeepprabhakula.moviesForYou.data.Post;
import com.sandeepprabhakula.moviesForYou.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    @CrossOrigin(origins = "*")
    @PostMapping("/add-post/")
    public void addPost(@RequestParam("title")String title,
                        @RequestParam("description")String description,
                        @RequestParam("postedTime")long time,
                        @RequestParam("videoURL")String videoURL,
                        @RequestParam("postType")String postType,
                        @RequestParam("imageData")MultipartFile file)
            throws IOException {
        postService.addNewPost(title,time,description,videoURL,postType,file);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-posts/")
    public List<Post> getPostsByType(@RequestParam("postType")String postType){
        return postService.getPostsByType(postType);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/get-all-posts")
    public List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/update-post")
    public void updatePost(@RequestParam("title") String title,
                           @RequestParam("id")String id,
                           @RequestParam("description")String description,
                           @RequestParam("postType")String postType,
                           @RequestParam("videoURL")String videoURL){
        postService.updatePost(id,title,description,postType,videoURL);
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/delete-post/{id}")
    public void deletePost(@PathVariable("id") String id){
        postService.deletePost(id);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/search-post/{title}")
    public List<Post> searchByTitle(@PathVariable("title")String title){
        return postService.search(title);
    }
}
