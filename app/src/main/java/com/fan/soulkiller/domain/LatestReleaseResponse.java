package com.fan.soulkiller.domain;

/**
 * Created by Chengming Fan on 2023/3/6 15:36
 */
public class LatestReleaseResponse {
    private String url;
    private String name;
    private String tagName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
