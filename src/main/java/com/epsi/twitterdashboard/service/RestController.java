package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.utils.JsonFile;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import java.io.IOException;
import java.text.ParseException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import twitter4j.JSONArray;
import twitter4j.JSONException;

/**
 * @author Allan
 */
@Path("rest")
public class RestController {

    @GET
    @Path("/{username}/fetchtimeline")
    /**
     * Get specific user timeline
     * @param username
     * @param count
     * @return
     */
    public String FetchTimeline(@PathParam("username") String username, @QueryParam("count") int count) throws IOException, JSONException, ParseException {
        JSONArray tweets = new JSONArray(RestTwitterApi.FetchTimeline(username, count));
        return tweets.toString();
    }

    @GET
    @Path("/{username}/bookmarks")
    /**
     * Get user bookmarks
     * @param username
     * @return
     */
    public String GetBookmarks(@PathParam("username") String username) {
        JSONArray bookmarks = new JSONArray(JsonFile.ReadBookmarks(username));
        return bookmarks.toString();
    }

    @PUT
    @Path("/{username}/bookmark/{id}")
    /**
     * Bookmarks a tweet
     * @param username
     * @param id
     * @return
     */
    public void Bookmark(@PathParam("username") String username, @PathParam("id") int id) {
        JsonFile.AddBookmark(username, id);
    }

    @DELETE
    @Path("/{username}/bookmark/{id}")
    /**
     * Get specific user timeline
     * @param username
     * @param id
     * @return
     */
    public void DeleteBookmark(@PathParam("username") String username, @PathParam("id") int id) {
        JsonFile.DeleteBookmark(username, id);
    }
}
