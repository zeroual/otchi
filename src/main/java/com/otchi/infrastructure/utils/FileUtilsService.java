package com.otchi.infrastructure.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URL;

public interface FileUtilsService {

    File getFileFrom(URL url);

    File getFileFrom(MultipartFile file);
}
