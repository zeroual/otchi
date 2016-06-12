package com.otchi.infrastructure.stubs;

import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MockBlobStorageService implements BlobStorageService {
    @Override
    public void save(List<MultipartFile> files) {

    }
}
