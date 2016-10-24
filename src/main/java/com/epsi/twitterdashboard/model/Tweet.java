package com.epsi.twitterdashboard.model;

import java.util.Date;

/**
 * Tweet model
 * @author Allan
 */
public class Tweet {

    // ********** PRIVATE FIELDS **********
    private String Body;
    private Date CreationDate;
    private User User;
    // ************************************

    // ********** ACCESSORS **********
    public String getBody() {
        return Body;
    }
    public void setBody(String body) {
        this.Body = body;
    }
    
    public Date getCreationDate() {
        return CreationDate;
    }
    public void setCreationDate(Date CreationDate) {
        this.CreationDate = CreationDate;
    }
    
    public User getUser() {
        return User;
    }
    public void setUser(User User) {
        this.User = User;
    }
    // *******************************
}
