package com.epsi.twitterdashboard.parser;

import com.epsi.twitterdashboard.model.Tweet;
import java.util.ArrayList;
import java.util.List;
import twitter4j.JSONArray;
import twitter4j.JSONException;
import twitter4j.JSONObject;

/**
 *
 * @author Allan
 */
public class TwitterParser {
    
    /**
     * Parse a json tweet to Java object
     * @param tweetJson
     * @return
     * @throws JSONException 
     */
    public static Tweet ParseTweet(String tweetJson) throws JSONException {
        return InitializeTweet(new JSONObject(tweetJson));
    }
    
    /**
     * Parse a list of json tweet to Java list of object
     * @param tweetsJson
     * @return
     * @throws JSONException 
     */
    public static List<Tweet> ParseTweets(String tweetsJson) throws JSONException {
        // Parse JSON to Java list
        JSONArray jsonArray = new JSONArray(tweetsJson);
        
        // Initialize tweet list
        List<Tweet> tweets = new ArrayList<Tweet>();
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
     */
    private static Tweet InitializeTweet(JSONObject json) throws JSONException {
        // Initialize object
        Tweet tweet = new Tweet();
        
        // Set Properties
        tweet.setBody(json.get("text").toString());
        
        return tweet;
    }
    
}
