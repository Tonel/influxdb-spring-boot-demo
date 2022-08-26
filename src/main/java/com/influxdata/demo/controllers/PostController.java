package com.influxdata.demo.controllers;

import com.influxdata.demo.dtos.PostRequest;
import com.influxdata.demo.entities.Post;
import com.influxdata.demo.repositories.PostRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Metrics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private final PostRepository postRepository;

    PostController(@Autowired PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Post>> getAll() {
        return new ResponseEntity<>(
                postRepository.findAll(),
                HttpStatus.CREATED
        );
    }

    @PostMapping()
    public ResponseEntity<Void> create(
            @RequestBody PostRequest postRequest
    ) {
        Post postToCreate = new Post();
        postToCreate.setUrl(postRequest.getUrl());
        postToCreate.setAuthorEmail(postRequest.getAuthorEmail());

        Post post = postRepository.save(postToCreate);

        Counter counter = Metrics.counter(
                "request.posts",
                "postId",
                post.getId().toString(),
                "status",
                "draft-received"
        );
        counter.increment();

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(
            @PathVariable(value = "id") Long id,
            @RequestBody PostRequest postRequest
    ) {
        Post postToUpdate = postRepository.findById(id).get();
        postToUpdate.setUrl(postRequest.getUrl());
        postToUpdate.setAuthorEmail(postRequest.getAuthorEmail());

        postRepository.save(postToUpdate);

        Counter counter = Metrics.counter(
                "request.posts",
                "postId",
                id.toString(),
                "status",
                "updated"
        );
        counter.increment();

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(
            @PathVariable(value = "id") Long id
    ) {
        postRepository.deleteById(id);

        Counter counter = Metrics.counter(
                "request.posts",
                "postId",
                id.toString(),
                "status",
                "deleted"
        );
        counter.increment();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
