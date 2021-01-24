package com.agoramati.downloader.model;

import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("Quotes")
public class Quotes implements Serializable {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}
