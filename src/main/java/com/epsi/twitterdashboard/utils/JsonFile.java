/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.utils;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.parser.TwitterParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import twitter4j.JSONArray;
import twitter4j.JSONException;

/**
 *
 * @author Allan
 */
public class JsonFile {
    
    private static final String DataBasePath = "\\BDD\\{0}_database_twitter.json";
    private static final String BookmarkPath = "\\BDD\\{0}_bookmark_twitter.json";
    
    /**
     * Writes dashboard data to json file
     * @param database 
     */
    public static void WriteDatabase(String username, JSONArray database) {
        Write(String.format(JsonFile.DataBasePath, username), database.toString());
    }
    
    /**
     * Add dashboard bookmark to json file
     * @param id 
     */
    public static void AddBookmark(String username, int id) {
        List<Tweet> database = ReadDatabase(username);
        List<Tweet> bookmarks = ReadBookmarks(username);
        bookmarks.add(TweetFinder.FindById(database, id));
        JSONArray jsonBookmark = new JSONArray(bookmarks);
        Write(String.format(JsonFile.BookmarkPath, username), jsonBookmark.toString());
    }
    
    /**
     * Delete dashboard bookmark from json file
     * @param id 
     */
    public static void DeleteBookmark(String username, int id) {
        List<Tweet> bookmarks = ReadBookmarks(username);
        bookmarks.remove(TweetFinder.FindById(bookmarks, id));
        JSONArray jsonBookmark = new JSONArray(bookmarks);
        Write(String.format(JsonFile.BookmarkPath, username), jsonBookmark.toString());
    }
    
    /**
     * Gets database data
     * @return 
     */
    public static List<Tweet> ReadDatabase(String username) {
        return Read(String.format(JsonFile.DataBasePath, username));
    }
    
    /**
     * Gets bookmarks data
     * @return 
     */
    public static List<Tweet> ReadBookmarks(String username) {
        return Read(String.format(JsonFile.BookmarkPath, username));
    }
    
    /**
     * Write data into file
     * @param path
     * @param array 
     */
    private static void Write(String path, String data) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(data);
            file.flush();
        }  catch (IOException ex) {
            Logger.getLogger(JsonFile.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    /**
     * Reads data from a file
     * @param path
     * @return 
     */
    private static List<Tweet> Read(String path) {
        File f = new File(path);
        if (f.exists()){
            try {
                InputStream is = new FileInputStream(path);
                return TwitterParser.ParseTweets(IOUtils.toString(is));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(JsonFile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | JSONException ex) {
                Logger.getLogger(JsonFile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
}
