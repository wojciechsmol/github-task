package com.smol.githubtask.controller;

import com.smol.githubtask.service.GetDataService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

@Controller
public class MainController {

    GetDataService mGetDataService;

    @Autowired
    public MainController(GetDataService getDataService) {
        mGetDataService = getDataService;
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public ModelAndView userInfo(OAuth2Authentication principal, Model model) {

        ModelAndView mav = new ModelAndView("userInfo");
        HashMap<String, Object> languagesUsage = mGetDataService.getLanguagesUsage(principal);
        ObjectMapper mapper = new ObjectMapper();

        //mapping HasMap to JSON so that it can be used by JavaScript
        String json = "";
        try {
            json = mapper.writeValueAsString(languagesUsage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mav.addObject("languagesUsage", json);
        mav.addObject("email", mGetDataService.getEmail(principal));
        mav.addObject("name", mGetDataService.getName(principal));
        mav.addObject("avatar_url", mGetDataService.getAvatarUrl(principal));

        return mav;
    }

    @RequestMapping(value = "/userInfo/repos", method = RequestMethod.GET)
    public ModelAndView repos(OAuth2Authentication principal) {
        ModelAndView mav = new ModelAndView("repos");
        mav.addObject("repos", mGetDataService.getAllPublicRepos(principal));
        return mav;
    }
}
