package com.smol.githubtask;

import com.smol.githubtask.model.Repo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetSamplePublicDataTest {

    @LocalServerPort
    private int port;

    @Value("${value.url.repos.test}")
    private String url;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Logger log = LoggerFactory.getLogger(GetSamplePublicDataTest.class);

    @Test
    public void CheckPublicRepoDataTest() throws Exception {
        ResponseEntity<List<Repo>> reposRespone =
                restTemplate.exchange(url,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repo>>() {
                        });

        List<Repo> repos = reposRespone.getBody();
        for (Repo repo : repos){
            assertNotNull(repo.getId());
            assertNotNull(repo.getName());

            // I do not check if Language is not null, because it sometimes can be null
            log.info(repo.toString());
        }
    }
}
