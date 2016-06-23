package com.otchi.infrastructure.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

public class FileUtilsServiceImpl implements FileUtilsService {


    @Override
    public File getFileFrom(URL url) {
        try {
            File file = File.createTempFile(UUID.randomUUID().toString(), null);
            org.apache.commons.io.FileUtils.copyURLToFile(url, file);
            return file;
        } catch (IOException e) {
            throw new UnableToGetFileException("Can not get file form this url:" + url, e.getCause());
        }
    }

    @Override
    public File getFileFrom(MultipartFile file) {
        try {
            final File temporaryFile = File.createTempFile(UUID.randomUUID().toString(), null);
            FileUtils.writeByteArrayToFile(temporaryFile, file.getBytes());
            return temporaryFile;
        } catch (IOException e) {
            throw new UnableToGetFileException("Can not get file:", e.getCause());
        }

    }
}
