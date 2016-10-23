/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.twitter4j;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Classe de test de l'API Twitter4j
 *
 * @author Allan
 */
public class TwitterApi {
    
    private static Twitter Instance = null;

    /**
     * Authentifie l'utilisateur sur Twitter
     *
     * @return L'instance Twitter initialis� avec l'utilisateur authentifi�
     */
    public static void Authenticate() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey("RAfoVr8OZR6X1UxkToVbRD5zI")
            .setOAuthConsumerSecret("KMQexOiK25A1g3R0ZbDFPZi4PMnjNkG7hw7qvUM9ZKClHyW4N6")
            .setOAuthAccessToken("473004867-fImVTJxSFYBPOt1t4G7kVUq0mUYIcDpORykUo6Qx")
            .setOAuthAccessTokenSecret("O2ethhUa0rNkUf9sxKAP10XvDgEaJr3LsUJiUHYS0ktdu");
        TwitterFactory tf = new TwitterFactory(cb.build());
        TwitterApi.Instance = tf.getInstance();
    }
    
    /**
     * Handle TwitterApi instance singleton
     * @return 
     */
    public static Twitter GetInstance() {
        if (TwitterApi.Instance == null) {
            TwitterApi.Authenticate();
        }
        return TwitterApi.Instance;
    }
}
