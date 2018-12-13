package de.whs.poodle.integration.criterion.repositories;

import de.whs.poodle.integration.criterion.CriterionProperties;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TestFileRepository {
    private final CriterionProperties criterion;

    @Autowired
    public TestFileRepository(CriterionProperties criterion) {
        this.criterion = criterion;
    }

    public boolean upload(int studentId, String exerciseId, MultipartFile file) {
        System.out.println("upload to criterion" +
                " file=" + file.getOriginalFilename() +
                " suite=" + exerciseId +
                " userId=" + studentId);
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(criterion.getBaseUrl() + "/suite/" + exerciseId + "/run");

            post.setEntity(
                    MultipartEntityBuilder
                            .create()
                            .addTextBody("userId", "" + studentId)
                            .addBinaryBody(
                                    "file",
                                    file.getInputStream(),
                                    ContentType.APPLICATION_OCTET_STREAM,
                                    file.getOriginalFilename()
                            ).build());

            CloseableHttpResponse response = httpClient.execute(post);

            if (response.getStatusLine().getStatusCode() != 200) {
                //error
                return false;
            }
        } catch (Exception ignored) {
            return false;
        }
        return true;
    }
}
