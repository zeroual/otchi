package com.otchi.infrastructure.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class AmazonBlobStorageService implements BlobStorageService {

    private final Logger log = LoggerFactory.getLogger(AmazonBlobStorageService.class);


    @Value("${amazon.s3.default-bucket}")
    private String bucketName;

    private final AmazonS3Client amazonS3Client;
    private TransferManager transferManager;

    @Autowired
    public AmazonBlobStorageService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
        this.transferManager = new TransferManager(this.amazonS3Client);
    }

    @Override
    public void save(List<MultipartFile> files) {

        // check bucket
        if (!doestBucketExist(bucketName)) {
            log.debug("operation aborted due to bucket not found");
            return;
        }
        for (MultipartFile file : files) {
            String key = file.getOriginalFilename();
            try {
                final File fileToUpload = File.createTempFile(UUID.randomUUID().toString(), null);
                FileUtils.writeByteArrayToFile(fileToUpload, file.getBytes());
                putObject(bucketName, fileToUpload, key);
            } catch (IOException e) {

            }
        }
    }

    private boolean doestBucketExist(String bucketName) {
        boolean result = false;
        try {
            result = this.amazonS3Client.doesBucketExist(bucketName);
        } catch (AmazonServiceException ase) {
            log.debug("unable to check bucket exists" +
                    "\nbucket name:" + bucketName +
                    "\nAWS Error Code: " + ase.getErrorCode() +
                    "\nAWS Error Message: " + ase.getMessage());
        } catch (AmazonClientException ace) {
            log.debug("unable to check bucket exists" +
                    "\nbucket name:" + bucketName +
                    "\nAWS Error Message: " + ace.getMessage());
        }
        return result;
    }

    private void putObject(String bucketName, File file, String key) {
        PutObjectRequest request = new PutObjectRequest(bucketName, key, file);
        Upload upload;
        try {
            upload = this.transferManager.upload(request);
            UploadResult uploadResult = upload.waitForUploadResult();
            log.info("object has been uploaded to amazon S3 successfully" +
                    "\nbucket name:" + bucketName +
                    "\nobject key: " + uploadResult.getKey());
        } catch (AmazonServiceException ase) {
            log.debug("unable to upload file" +
                    "\nfile name:" + key +
                    "\nbucket name:" + bucketName +
                    "\nAWS Error Code: " + ase.getErrorCode() +
                    "\nAWS Error Message: " + ase.getMessage());
        } catch (AmazonClientException ace) {
            log.debug("unable to upload file" +
                    "\nfile name:" + key +
                    "\nAWS Error Message: " + ace.getMessage());
        } catch (InterruptedException e) {
            log.debug("error while uploading object" +
                    "\nError Message:" + e.getMessage());
        } finally {
            file.delete();
        }

    }
}
