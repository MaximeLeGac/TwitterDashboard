package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.utils.JsonFile;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import twitter4j.JSONException;

/**
 * @author Allan
 */
@Path("rest")
public class RestController {

    @GET
    @Path("/fetchtimeline/{username}")
    /**
     * Get specific user timeline
     */
    public List<Tweet> FetchTimeline(@PathParam("username") String username, @QueryParam("count") int count) throws IOException, JSONException, ParseException {
        return RestTwitterApi.FetchTimeline(username, count);
    }

    @GET
    @Path("/bookmark/{id}")
    /**
     * Bookmarks a tweet
     */
    public void Bookmark(@PathParam("id") int id) {
        JsonFile.AddBookmark(id);
    }

    @GET
    @Path("/deletebookmark/{id}")
    /**
     * Get specific user timeline
     */
    public void DeleteBookmark(@PathParam("id") int id) {
        JsonFile.DeleteBookmark(id);
    }
}
