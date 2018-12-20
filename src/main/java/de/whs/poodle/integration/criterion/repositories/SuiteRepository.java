package de.whs.poodle.integration.criterion.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.whs.poodle.WorksheetAutoUnlockScheduler;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.Suite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class SuiteRepository {
    private final CriterionProperties criterion;

    private Logger log = LoggerFactory.getLogger(SuiteRepository.class);


    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public SuiteRepository(CriterionProperties criterion) {
        this.criterion = criterion;
    }

    public Suite get(int id) {
        try {
            URL url = new URL(String.format("%s/suite/%d", criterion.getBaseUrl(), id));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                if (conn.getResponseCode() != 404) {
                    log.error("unexpected ResponseCode("
                            + conn.getResponseCode()
                            + ") while getting suite from criterion with id=" + id);
                }
                return null;
            }

            return mapper.readValue(new InputStreamReader(conn.getInputStream()), Suite.class);
        } catch (ConnectException e) {
            log.error(String.format("could not connect to criterion: %s/suite/%d", criterion.getBaseUrl(), id));
        } catch (IOException e) {
            log.error(String.format("error getting suite from criterion id=%d", id), e);
        }
        return null;
    }
}
