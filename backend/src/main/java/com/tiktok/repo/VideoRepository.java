package com.tiktok.repo;

import com.tiktok.model.Video;

import java.util.Optional;

public class VideoRepository {
    public Video findVideoById(String id) {
        return new Video();
    }

    public Optional<Video> findById(String videoId) {
        return Optional.ofNullable(videoRepository.findVideoById(videoId));
    }
}
