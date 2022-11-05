package com.sandeepprabhakula.moviesForYou.service;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sandeepprabhakula.moviesForYou.data.Post;
import com.sandeepprabhakula.moviesForYou.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.Document;
import org.bson.types.Binary;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MongoClient mongoClient;
    private final MongoConverter converter;

    public void addNewPost(String title, long time, String description, String videoURL, String postType, MultipartFile file) throws IOException {
        Post newPost = new Post();
        newPost.setTitle(title);
        newPost.setDescription(description);
        newPost.setVideoURL(videoURL);
        newPost.setPostedTime(time);
        newPost.setPostType(postType);
        newPost.setImageURL(new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        postRepository.save(newPost);
    }

    public List<Post> getPostsByType(String postType) {
        List<Post> posts = new ArrayList<>();
        for (Post post : postRepository.findAll())
            if (post.getPostType().equalsIgnoreCase(postType)) posts.add(post);
        return posts;
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public void updatePost(String id, String title, String description, String postType, String videoURL) {
        Post currentPost = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("Can't find the review with id " + id)
                ));
        currentPost.setTitle(title);
        currentPost.setPostType(postType);
        currentPost.setDescription(description);
        currentPost.setVideoURL(videoURL);
        postRepository.save(currentPost);
    }

    public void deletePost(String id) {
        postRepository.deleteById(id);
    }

    public List<Post> search(String name) {
        MongoDatabase database = mongoClient.getDatabase("movies-4u");
        MongoCollection<Document> collection = database.getCollection("posts");
        List<Post>reviews = new ArrayList<>();
        AggregateIterable<Document> result = collection.aggregate(Collections.singletonList(new Document("$search",
                new Document("index", "default")
                        .append("wildcard",
                                new Document("query", name + "*")
                                        .append("path", "title")
                                        .append("allowAnalyzedField", true)))));
        result.forEach(doc-> reviews.add(converter.read(Post.class,doc)));
        return reviews;
    }
}
