/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.utils;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.model.User;
import java.util.List;

/**
 *
 * @author Allan
 */
public class ListFinder {
    
    /**
     * Get a tweet in current list corresponding to the given id
     * @param tweets
     * @param id
     * @return 
     */
    public static Tweet FindTweetById(List<Tweet> tweets, int id) {
        for(Tweet tweet : tweets) {
            if(tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }
    
    /**
     * Get a user in current list corresponding to the given id
     * @param users
     * @param id
     * @return 
     */
    public static User FindUserById(List<User> users, int id) {
        for(User user : users) {
            if(user.getId() == id) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Get a user in current list corresponding to the given id
     * @param users
     * @param id
     * @return 
     */
    public static User FindUserByUsername(List<User> users, String username) {
        for(User user : users) {
            if(user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}
