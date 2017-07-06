package com.otchi.infrastructure.storage;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.model.UploadResult;
import com.otchi.infrastructure.utils.FileUtilsServiceImpl;
import com.otchi.infrastructure.utils.UnableToGetFileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//TODO refactor this class and test it
public class AmazonBlobStorageService implements BlobStorageService {

    public static final String RESIZED_SUFFIX = "-resized";
    private final Logger log = LoggerFactory.getLogger(AmazonBlobStorageService.class);


    private String bucketName;
    private String bucketNameForResized;
    private String s3EndpointURL;
    private final AmazonS3Client amazonS3Client;
    private TransferManager transferManager;
    private final FileUtilsServiceImpl fileUtilsService;

    public AmazonBlobStorageService(AmazonS3Client amazonS3Client, String bucketName, String s3EndpointURL, FileUtilsServiceImpl fileUtilsService) {
        this.amazonS3Client = amazonS3Client;
        this.bucketName = bucketName;
        this.s3EndpointURL = s3EndpointURL;
        this.fileUtilsService = fileUtilsService;
        this.transferManager = new TransferManager(this.amazonS3Client);
        this.bucketNameForResized = bucketName + RESIZED_SUFFIX;
    }

    @Override
    public List<String> save(List<MultipartFile> files) throws BlobObjectPutException {

        List objectURLList = new ArrayList<>();
        // check bucket
        if (!doestBucketExist(bucketName)) {
            log.debug("operation aborted due to bucket not found");
        } else {
            for (MultipartFile file : files) {
                try {

                    final File fileToUpload = fileUtilsService.getFileFrom(file);
                    String key = generateObjectUniqueKey() + getFileExtension(file.getOriginalFilename());
                    putObject(bucketName, fileToUpload, key);
                    String url = generateURLFrom(key);
                    objectURLList.add(url);
                    fileToUpload.delete();
                } catch (UnableToGetFileException e) {
                    throw new BlobObjectPutException("cannot create temporary file", e);
                }
            }
        }
        return objectURLList;
    }

    @Override
    public String save(File file) {
        String ObjectURL = "";
        // check bucket
        if (!doestBucketExist(bucketName)) {
            log.debug("operation aborted due to bucket not found");
        } else {
            String key = generateObjectUniqueKey();
            putObject(bucketName, file, key);
            ObjectURL = generateURLFrom(key);
        }
        return ObjectURL;
    }

    private String generateURLFrom(String key) {
        return "https://" + bucketNameForResized + "." + s3EndpointURL + "/" + key;
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

    private String getFileExtension(final String originalFileName){
        String extension = "";
        int i = originalFileName.lastIndexOf('.');
        if (i > 0) {
            extension = originalFileName.substring(i);
        }
        return extension;
    }
}
