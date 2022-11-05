package com.sandeepprabhakula.moviesForYou.repository;

import com.sandeepprabhakula.moviesForYou.data.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String> {
}
