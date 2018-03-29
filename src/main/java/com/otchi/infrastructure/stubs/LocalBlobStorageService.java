package com.otchi.infrastructure.stubs;

import com.otchi.infrastructure.storage.BlobStorageService;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.io.File.separator;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;
import static org.apache.commons.io.FileUtils.deleteDirectory;

public class LocalBlobStorageService implements BlobStorageService {

    private final String storageFolder;
    private static final String PATH = "/assets/blob/";

    public LocalBlobStorageService(ServletContext servletContext) {
        this.storageFolder = servletContext.getRealPath(PATH);
    }

    @Override
    public List<String> save(List<MultipartFile> files) {
        List list = new ArrayList();
        files.stream().forEach(
                multipartFile -> {
                    String temporaryFile = save(multipartFile);
                    list.add(temporaryFile);
                }
        );
        return list;
    }

    @Override
    public String save(File file) {
        return null;
    }

    private String save(MultipartFile multipartFile) {
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            File newFile = new File(storageFolder + separator + originalFilename);
            copyInputStreamToFile(multipartFile.getInputStream(), newFile);
            return PATH + multipartFile.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Opps , Otchi can not create new file in the assets directory :(", e);
        }
    }

    @PreDestroy
    public void removeTStorageFolder() throws IOException {
        deleteDirectory(new File(storageFolder));
    }
}
