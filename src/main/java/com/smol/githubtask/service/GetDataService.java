package com.smol.githubtask.service;

import com.smol.githubtask.model.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class GetDataService {

    private OAuth2RestTemplate mOAuth2RestTemplate;

    @Autowired
    public GetDataService(OAuth2RestTemplate OAuth2RestTemplate) {
        mOAuth2RestTemplate = OAuth2RestTemplate;
    }

    private static String getReposUrl(OAuth2Authentication principal) {
        HashMap<String, Object> details = (HashMap<String, Object>) principal.getUserAuthentication().getDetails();
        return details.get("repos_url").toString();
    }

    public String getEmail(OAuth2Authentication principal) {
        HashMap<String, Object> details = (HashMap<String, Object>) principal.getUserAuthentication().getDetails();
        String email = "";

        //in case it doesn't contain email
        try {
            email = details.get("email").toString();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        return email;
    }

    public String getName(OAuth2Authentication principal) {
        return principal.getName();
    }

    public String getAvatarUrl(OAuth2Authentication principal) {
        HashMap<String, Object> details = (HashMap<String, Object>) principal.getUserAuthentication().getDetails();
        return details.get("avatar_url").toString();
    }

    public List<Repo> getAllPublicRepos(OAuth2Authentication principal) {

        String reposUrl = getReposUrl(principal) + "?visibility=public";
        ResponseEntity<List<Repo>> reposResponse =
                mOAuth2RestTemplate.exchange(reposUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repo>>() {
                        });
        return reposResponse.getBody();
    }

    public HashMap<String, Object> getLanguagesUsage(OAuth2Authentication principal) {

        String reposUrl = getReposUrl(principal);
        ResponseEntity<List<Repo>> reposResponse =
                mOAuth2RestTemplate.exchange(reposUrl,
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Repo>>() {
                        });
        List<Repo> repos = reposResponse.getBody();

        //Object instead of Integer so that it can be mapped to JSON later
        HashMap<String, Object> languagesUsage = new HashMap<>();

        for (Repo repo : repos) {
            String key = repo.getLanguage();
            if (key == null)
                key = "Unknown";

            Object usage = languagesUsage.get(key);
            if (usage != null) {
                try {
                    //can throw ClassCastException or NullPointerException
                    languagesUsage.put(key, ((Integer) languagesUsage.get(key)) + 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else
                languagesUsage.put(key, 1);
        }

        return languagesUsage;
    }


}
