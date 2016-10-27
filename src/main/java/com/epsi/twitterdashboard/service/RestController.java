package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.User;
import com.epsi.twitterdashboard.utils.JsonFile;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import com.epsi.twitterdashboard.utils.ListFinder;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
    
    @POST
    @Path("/subscribe")
    @Consumes("application/json")
    public void Subscribe(User user) {
        JsonFile.AddUser(user);
    }
    
    @POST
    @Path("/login")
    /**
     * Log current user
     * @param username
     * @return
     */
    public String Login(@PathParam("username") String username) throws IOException, JSONException, ParseException {
        List<User> users = JsonFile.ReadUsers();
        if (ListFinder.FindUserByUsername(users, username) != null) {
            return FetchTimeline(username, 0);
        }
        return "";
    }

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
