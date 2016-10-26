package com.epsi.twitterdashboard.model;

import java.util.Date;
import java.util.List;

/**
 * Tweet model
 * @author Allan
 */
public class Tweet {

    // ********** PRIVATE FIELDS **********
    private int id;
    private String Body;
    private Date CreationDate;
    private User User;
    private List<User> Mentions;
    // ************************************

    // ********** ACCESSORS **********
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
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
    
    public List<User> getMentions() {
        return Mentions;
    }
    public void setMentions(List<User> mentions) {
        this.Mentions = mentions;
    }
    // *******************************
}
