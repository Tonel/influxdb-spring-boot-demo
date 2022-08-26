package com.influxdata.demo.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String authorEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return getAuthorEmail() == post.getAuthorEmail() && Objects.equals(getId(), post.getId()) && Objects.equals(getUrl(), post.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUrl(), getAuthorEmail());
    }
}
