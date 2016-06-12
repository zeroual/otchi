package com.otchi.infrastructure.stubs;

import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

public class MockBlobStorageService implements BlobStorageService {
    @Override
    public List<String> save(List<MultipartFile> files) {
        return Collections.emptyList();
    }
}
