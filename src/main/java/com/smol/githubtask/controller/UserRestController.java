package com.smol.githubtask.controller;

import com.smol.githubtask.service.GetDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserRestController {

    private GetDataService mGetDataService;

    @Autowired
    public UserRestController(GetDataService getDataService) {
        mGetDataService = getDataService;
    }

    @GetMapping("/user")
    public Principal user(OAuth2Authentication principal) {
        return principal;
        //TODO delete that!
    }


}
