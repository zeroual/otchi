package com.otchi.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BlobStorageService {

    void save(List<MultipartFile> files);
}
