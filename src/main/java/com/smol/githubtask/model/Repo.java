package com.smol.githubtask.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//Represents info about repo
@JsonIgnoreProperties(ignoreUnknown = true)
public class Repo {

    private Long id;
    private String name;
    private String language;
    private String url;


    public Repo() {

    }

    public Repo(Long id, String name, String language, String url) {
        this.id = id;
        this.name = name;
        this.language = language;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
