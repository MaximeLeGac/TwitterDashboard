package com.epsi.twitterdashboard.parser;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

/**
 * Tweet parser
 * @author Allan
 */
public class TwitterParser {
    
    /**
     * Parse a json tweet to Java object
     * @param tweetJson
     * @return
     * @throws JSONException 
     * @throws ParseException 
     */
    public static Tweet ParseTweet(String tweetJson) throws JSONException, ParseException {
        return InitializeTweet(new JSONObject(tweetJson));
    }
    
    /**
     * Parse a list of json tweet to Java list of object
     * @param tweetsJson
     * @return
     * @throws JSONException 
     * @throws ParseException 
     */
    public static List<Tweet> ParseTweets(String tweetsJson) throws JSONException, ParseException {
        // Parse JSON to Java list
        JSONArray jsonArray = new JSONArray(tweetsJson);
        
        // Initialize tweet list
        List<Tweet> tweets = new ArrayList();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            tweets.add(InitializeTweet(new JSONObject(json)));
        }
        return tweets;
    }
    
    /**
     * Initialize tweet object from JSONObject properties
     * @param json
     * @return
     * @throws JSONException 
     * @throws ParseException 
     */
    private static Tweet InitializeTweet(JSONObject json) throws JSONException, ParseException {
        // Initialize object
        Tweet tweet = new Tweet();
        
        // Text property
        tweet.setBody(json.getString("text"));
        
        // Creation date property
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date creationDate = sdf.parse(json.getString("created_at"));
        tweet.setCreationDate(creationDate);
        
        // User property
        JSONObject userJson = json.getJSONObject("user");
        tweet.setUser(InitializeUser(userJson));
        
        return tweet;
    }
    
    private static User InitializeUser(JSONObject json) {
        // Initialize object
        User user = new User();
        
        //user.setBody(json.getString("text"));
        
        return user;
    }
}
