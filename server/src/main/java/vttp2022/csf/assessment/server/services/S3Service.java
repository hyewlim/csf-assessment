package vttp2022.csf.assessment.server.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class S3Service {

    @Autowired
    private AmazonS3 s3Client;

    public String upload(byte[] file) throws IOException {

        InputStream inputStream = new ByteArrayInputStream(file);

        Map<String, String> userData = new HashMap<>();
//        userData.put("name", post.getName());
//        userData.put("uploadTime", String.valueOf(new Date()));

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length);
        metadata.setContentType("png");
        metadata.setUserMetadata(userData);

        String key = UUID.randomUUID().toString().substring(0,8);

        PutObjectRequest putObjectRequest = new PutObjectRequest(
                "hyhy",
                "csfassessment/%s".formatted(key),
                inputStream,
                metadata
        );

        putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);

        s3Client.putObject(putObjectRequest);

        String BASE_URL = "https://hyhy.sgp1.digitaloceanspaces.com/csfassessment%252F";

        String image_url = BASE_URL + key;

        return image_url;

    }

}
