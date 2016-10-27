package com.epsi.twitterdashboard.parser;

import com.epsi.twitterdashboard.utils.JsonFile;
import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.model.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public static List<Tweet> ParseDatabase(String username, String tweetsJson) throws JSONException {
        // Parse JSON to Java list
        JSONArray jsonArray = new JSONArray(tweetsJson);
        
        // Keeps tweets into a local file
        JsonFile.WriteDatabase(username, jsonArray);
        
        // Initialize tweet list
        List<Tweet> tweets = new ArrayList();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            tweets.add(InitializeTweet(json));
        }
        return tweets;
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
        List<Tweet> tweets = new ArrayList();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            tweets.add(InitializeTweet(json));
        }
        return tweets;
    }
    
    /**
     * Parse a list of json user to Java list of object
     * @param usersJson
     * @return
     * @throws JSONException
     */
    public static List<User> ParseUsers(String usersJson) throws JSONException {
        // Parse JSON to Java list
        JSONArray jsonArray = new JSONArray(usersJson);
        
        // Initialize tweet list
        List<User> users = new ArrayList();
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            users.add(InitializeUser(json));
        }
        return users;
    }
    
    /**
     * Initialize tweet object from JSONObject properties
     * @param json
     * @return
     * @throws JSONException 
     * @throws ParseException 
     */
    private static Tweet InitializeTweet(JSONObject json) {
        // Initialize object
        Tweet tweet = new Tweet();
        
        // Id property
        try {
            tweet.setId(json.getInt("id"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Text property
        try {
            tweet.setBody(json.getString("text"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Creation date property
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date creationDate = sdf.parse(json.getString("created_at"));
            tweet.setCreationDate(creationDate);
        } catch (ParseException | JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // User property
        try {
            JSONObject userJson = json.getJSONObject("user");
            tweet.setUser(InitializeUser(userJson));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Mentions property
        try {
            List<User> mentions = new ArrayList();
            JSONObject entitiesJson = json.getJSONObject("entities");
            JSONArray mentionsJson = entitiesJson.getJSONArray("user_mentions");
            for (int i=0; i<mentionsJson.length(); i++) {
                JSONObject mentionjson = mentionsJson.getJSONObject(i);
                mentions.add(InitializeUser(mentionjson));
            }
            tweet.setMentions(mentions);
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tweet;
    }
    
    /**
     * Initialize user object from JSONObject properties
     * @param json
     * @return 
     */
    private static User InitializeUser(JSONObject json) {
        // Initialize object
        User user = new User();
        
        try {
            // Id property
            user.setId(json.getInt("id"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Name property
            user.setName(json.getString("name"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // ScreenName property
            user.setScreenName(json.getString("screen_name"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Location property
            user.setLocation(json.getString("location"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            // Description property
            user.setDescription(json.getString("description"));
        } catch (JSONException ex) {
            Logger.getLogger(TwitterParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return user;
    }
}
