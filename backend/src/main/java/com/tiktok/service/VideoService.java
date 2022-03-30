package com.tiktok.service;

import com.tiktok.dto.VideoDto;
import com.tiktok.model.Video;
import com.tiktok.repo.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final S3Service s3Service;
    private final VideoRepository videoRepository;

    public void uploadVideo(MultipartFile multipartFile) {
        // Upload to AWS S3
        // Save video data to database
        String videoUrl = s3Service.uploadFile(multipartFile);
        var video = new Video();
        video.setVideoUrl(videoUrl);
    }

    public VideoDto editVideo(VideoDto videoDto) {
        // Find video by id
        Video savedVideo = videoRepository.findVideoById(videoDto.getId());

        // Map fields
        savedVideo.setDescription(videoDto.getDescription());

        // Save video to db
        videoRepository.save(savedVideo);

        return videoDto;
    }

    public Video getVideoById(String videoId) {
        return videoRepository.findById(videoId).orElseThrow(() -> new IllegalArgumentException("Cannot find by id " + videoId));
    }

    public VideoDto getVideoDetails(String videoId) {
        Video video = getVideoById(videoId);

        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(video.getTitle());
        videoDto.setDescription(video.getDescription());
        videoDto.setVideoUrl(video.getVideoUrl());

        return videoDto;
    }
}
