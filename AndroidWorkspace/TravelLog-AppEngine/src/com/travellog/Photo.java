package com.travellog;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class Photo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key key;
    
    private String blobkey;
    private String title;
    private String description;
    
    public Key getKey() {
        return key;
    }

    void clearKey() {
        key = null;
    }

    public String getBlobkey() {
        return blobkey;
    }

    public void setBlobkey(String blobkey) {
        this.blobkey = blobkey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

