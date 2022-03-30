package com.tiktok.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
public class S3Service implements FileService {
    private final AmazonS3Client amazonS3Client;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        var filenameExtension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        var key = UUID.randomUUID().toString() + filenameExtension;
        var metadata = new ObjectMetadata();

        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3Client.putObject("bucket-name", key, multipartFile.getInputStream(), metadata);
        } catch (IOException ioException) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Exception occurred");
        }

        amazonS3Client.setObjectAcl("bucket-name", key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl("bucket-name", key);
    }
}
