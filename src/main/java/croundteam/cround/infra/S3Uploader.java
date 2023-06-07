package croundteam.cround.infra;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import croundteam.cround.common.exception.ErrorCode;
import croundteam.cround.creator.exception.InvalidImageExtensionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class S3Uploader {

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private static final String FILE_NAME_DELIMITER = "_";

    public S3Uploader(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadImage(MultipartFile file, String dirPath) {
        validateFileExtension(file);

        String imageUrl = generateImageUrl(file, dirPath);
        String profileImage = upload(imageUrl, file);

        return profileImage;
    }

    private String upload(String imageUrl, MultipartFile file) {
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            amazonS3.putObject(new PutObjectRequest(bucket, imageUrl, file.getInputStream(), metadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return amazonS3.getUrl(bucket, imageUrl).toString();
    }

    private String generateImageUrl(MultipartFile file, String dirPath) {
        return dirPath
                + UUID.randomUUID().toString()
                + FILE_NAME_DELIMITER
                + file.getOriginalFilename();
    }

    private void validateFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

        if (!isSupportExtension(extension)) {
            throw new InvalidImageExtensionException(ErrorCode.INVALID_IMAGE_EXTENSION);
        }
    }

    private static boolean isSupportExtension(String extension) {
        return "png".equals(extension) || "jpg".equals(extension);
    }
}
