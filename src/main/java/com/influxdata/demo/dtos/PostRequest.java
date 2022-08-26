package com.influxdata.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostRequest {
    @JsonProperty("url")
    private String url;

    @JsonProperty("authorEmail")
    private String authorEmail;

    public PostRequest() {}

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
}
