package aws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import com.google.common.io.Files;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

public class TestS3 {
    public static void main(String[] args) throws IOException {
        // 인증
        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(
                "access key",
                "secret access key");

        // s3 클라이언트 인증
        S3Client s3 = S3Client.builder().region(Region.AP_NORTHEAST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
        
//        upload(null, s3);
        File initialFile = new File("C:\\dev\\lombok.jar");
        InputStream targetStream = Files.asByteSource(initialFile).openStream();
        long size = FileUtils.sizeOf(initialFile);
        
        upload(targetStream, size, s3);
    }
    
    public static void upload(InputStream fileStream, long fileSize, S3Client client) throws IOException {
        // 요청 구성
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket("sfoofi-file")
            .key("file-title")
            .build();
        // 요청 바디 구성
        RequestBody requestBody = RequestBody
            .fromInputStream(fileStream, fileSize);
        
        client.putObject(putObjectRequest, requestBody);
    }
}
