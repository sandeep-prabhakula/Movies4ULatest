package com.sandeepprabhakula.moviesForYou.data;

import lombok.*;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("posts")
public class Post {
    @Id
    private String id;
    private long postedTime;
    private String title;
    private String description;
    private String postType;
    private Binary imageURL;
    private String videoURL;
}
