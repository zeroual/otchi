package com.otchi.infrastructure.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AmazonBlobStorageService implements BlobStorageService {

    private final Logger log = LoggerFactory.getLogger(AmazonBlobStorageService.class);


    @Value("${amazon.s3.default-bucket}")
    private String bucketName;

    @Value("${amazon.s3.url}")
    private String s3EndpointURL;

    private final AmazonS3Client amazonS3Client;
    private TransferManager transferManager;

    @Autowired
    public AmazonBlobStorageService(AmazonS3Client amazonS3Client) {
        this.amazonS3Client = amazonS3Client;
        this.transferManager = new TransferManager(this.amazonS3Client);
    }

    @Override
    public List<String> save(List<MultipartFile> files) throws BlobObjectPutException {

        List objectURLList = new ArrayList<>();
        // check bucket
        if (!doestBucketExist(bucketName)) {
            log.debug("operation aborted due to bucket not found");
        }else {
            for (MultipartFile file : files) {
                try {
                    final File fileToUpload = File.createTempFile(UUID.randomUUID().toString(), null);
                    FileUtils.writeByteArrayToFile(fileToUpload, file.getBytes());
                    String fileExtension = getFileExtension(file);
                    String key = generateObjectUniqueKey().concat(fileExtension);
                    putObject(bucketName, fileToUpload, key);
                    String url = generateURLFrom(key);
                    objectURLList.add(url);
                } catch (IOException e) {
                    throw new BlobObjectPutException("cannot create temporary file", e);
                }
            }
        }
        return objectURLList;
    }

    private String getFileExtension(MultipartFile file) {
        String fileOriginalName = file.getOriginalFilename();
        return fileOriginalName.substring(fileOriginalName.lastIndexOf('.'),fileOriginalName.length());
    }

    private String generateURLFrom(String key){
        return "https://" + bucketName + "." + s3EndpointURL + "/" + key;
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
        request.withCannedAcl(CannedAccessControlList.PublicRead);
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

    private String generateObjectUniqueKey() {
        return UUID.randomUUID().toString();
    }
}
