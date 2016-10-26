
package com.epsi.twitterdashboard.model;

/**
 * User model
 * @author Allan
 */
public class User {
    
    // ********** PRIVATE FIELDS **********
    private int id;
    private String Name;
    private String ScreenName;
    private String Location;
    private String Description;
    // ************************************

    // ********** ACCESSORS **********
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    
    public String getScreenName() {
        return ScreenName;
    }
    public void setScreenName(String screenName) {
        this.ScreenName = screenName;
    }
    
    public String getLocation() {
        return Location;
    }
    public void setLocation(String location) {
        this.Location = location;
    }
    
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        this.Description = description;
    }
    // *******************************
}
