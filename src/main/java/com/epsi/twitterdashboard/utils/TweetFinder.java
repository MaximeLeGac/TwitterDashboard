/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.utils;

import com.epsi.twitterdashboard.model.Tweet;
import java.util.List;

/**
 *
 * @author Allan
 */
public class TweetFinder {
    
    /**
     * Get a tweet in current list corresponding to the given id
     * @param tweets
     * @param id
     * @return 
     */
    public static Tweet FindById(List<Tweet> tweets, int id) {
        for(Tweet tweet : tweets) {
            if(tweet.getId() == id) {
                return tweet;
            }
        }
        return null;
    }
}
