package com.otchi.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

public interface BlobStorageService {

    void save(MultipartFile[] file);
}
