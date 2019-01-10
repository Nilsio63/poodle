package de.whs.poodle.integration.criterion.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.whs.poodle.integration.criterion.CriterionProperties;
import de.whs.poodle.integration.criterion.beans.SuiteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class SuiteResultRepository {
    private final CriterionProperties criterion;
    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public SuiteResultRepository(CriterionProperties criterion) {
        this.criterion = criterion;
    }

    public SuiteResult get(int id, String userId) {
        try {
            URL url = new URL(String.format("%s/suite/%d/result?userId=%s", criterion.getBaseUrl(), id, userId));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                //TODO:log
                return null;
            }
            // change to bean suiteResult
            return mapper.readValue(new InputStreamReader(conn.getInputStream()), SuiteResult.class);
        } catch (IOException e) {
            System.out.println("error getting suiteresult from criterion id=" + id);
        }
        return null;
    }
}
