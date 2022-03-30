package com.tiktok.controller;

import com.tiktok.dto.VideoDto;
import com.tiktok.model.Video;
import com.tiktok.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadVideo(@RequestParam("file") MultipartFile file) {
        videoService.uploadVideo(file);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void editVideoMetadata(@RequestBody VideoDto videoDto) {
        videoService.editVideo(videoDto);
    }
}
