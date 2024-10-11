package review.domain.common.dummy;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import review.domain.common.exception.CustomGlobalException;
import review.domain.common.exception.ErrorType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class DummyS3Service {

    private Map<String,byte[]> storage = new HashMap<>();

    public String uploadImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return null; // 파일이 없거나 비어있는 경우 null 반환
        }

        try {
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            storage.put(fileName, file.getBytes());
            return "https://dummy-s3.amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new CustomGlobalException(ErrorType.FAIL_FILE_UPLOAD,e);
        }
    }

    public void deleteImage(String imageUrl) {
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        storage.remove(fileName);
    }
}
