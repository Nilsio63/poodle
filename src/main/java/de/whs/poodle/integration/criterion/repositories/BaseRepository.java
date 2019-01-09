package de.whs.poodle.integration.criterion.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.whs.poodle.integration.criterion.CriterionProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class BaseRepository<T> {
    private final CriterionProperties criterion;
    private final Class<T> type;

    private Logger log = LoggerFactory.getLogger(BaseRepository.class);

    private static ObjectMapper mapper = new ObjectMapper();

    BaseRepository(CriterionProperties criterion, Class<T> type) {
        this.criterion = criterion;
        this.type = type;
    }

    protected T get(String urlSuffix) {
        return sendRequest(urlSuffix, "GET", null, true);
    }

    protected T post(String urlSuffix, T data){
        return sendRequest(urlSuffix, "POST", data, true);
    }

    protected void delete(String urlSuffix) {
        sendRequest(urlSuffix, "DELETE", null, false);
    }

    private T sendRequest(String urlSuffix, String requestMethod, T data, boolean hasResponseBody) {
        try {
            URL url = new URL(String.format("%s/%s", criterion.getBaseUrl(), urlSuffix));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(requestMethod);
            if (data != null) {
                byte[] postData = mapper.writeValueAsString(data).getBytes( StandardCharsets.UTF_8 );

                conn.setDoOutput(true);

                try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
                    wr.write( postData );
                }
            }

            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() != 404) {
                    log.error("unexpected ResponseCode("
                            + conn.getResponseCode()
                            + ") while sending " + requestMethod + " request to criterion with url = /" + urlSuffix);
                }
                return null;
            }

            if (hasResponseBody) {
                return mapper.readValue(new InputStreamReader(conn.getInputStream()), type);
            }
        } catch (ConnectException e) {
            log.error(String.format("could not connect to criterion: %s/%s", criterion.getBaseUrl(), urlSuffix));
        } catch (IOException e) {
            log.error(String.format("error sending %s request to criterion with url = /%s", requestMethod, urlSuffix), e);
        }

        return null;
    }
}
