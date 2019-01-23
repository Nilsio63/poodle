package de.whs.poodle.integration.criterion.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.whs.poodle.integration.criterion.CriterionConnectionException;
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

    protected T tryGet(String urlSuffix) {
        try {
            return get(urlSuffix);
        }
        catch (CriterionConnectionException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    protected T get(String urlSuffix) throws CriterionConnectionException {
        return sendRequest(urlSuffix, "GET", null, true);
    }

    protected T post(String urlSuffix, T data) throws CriterionConnectionException {
        return sendRequest(urlSuffix, "POST", data, true);
    }

    protected void delete(String urlSuffix) throws CriterionConnectionException {
        sendRequest(urlSuffix, "DELETE", null, false);
    }

    protected void tryDelete(String urlSuffix) {
        try {
            delete(urlSuffix);
        } catch (CriterionConnectionException e) {
            log.error(e.getMessage(), e);
        }
    }

    private T sendRequest(String urlSuffix, String requestMethod, T data, boolean hasResponseBody) throws CriterionConnectionException {
        try {
            URL url = new URL(String.format("%s/%s", criterion.getBaseUrl(), urlSuffix));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod(requestMethod);
            if (data != null) {
                byte[] postData = mapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);

                conn.setDoOutput(true);

                try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                    wr.write(postData);
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
            throw new CriterionConnectionException(String.format("could not connect to criterion: %s/%s", criterion.getBaseUrl(), urlSuffix), e);
        } catch (IOException e) {
            throw new CriterionConnectionException(String.format("error sending %s request to criterion with url = /%s", requestMethod, urlSuffix), e);
        }

        return null;
    }
}
