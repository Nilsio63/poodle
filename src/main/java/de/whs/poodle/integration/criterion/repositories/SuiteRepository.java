package de.whs.poodle.integration.criterion.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.Suite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class SuiteRepository {
    private final CriterionProperties criterion;

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
                //TODO:log
                return null;
            }

            return mapper.readValue(new InputStreamReader(conn.getInputStream()), Suite.class);
        } catch (IOException e) {
            System.out.println("error getting suite from criterion id=" + id);
        }
        return null;
    }
}
