package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.model.User;
import com.epsi.twitterdashboard.utils.JsonFile;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import com.epsi.twitterdashboard.utils.ListFinder;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import twitter4j.JSONException;

/**
 * @author Allan
 */
@Path("rest")
public class RestController {
    
    @POST
    @Path("/subscribe")
    @Consumes(MediaType.APPLICATION_JSON)
    public void Subscribe(User user) {
        JsonFile.AddUser(user);
    }
    
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Log current user
     * @param username
     * @return
     */
    public List<Tweet> Login(@PathParam("username") String username) throws IOException, JSONException, ParseException {
        List<User> users = JsonFile.ReadUsers();
        if (ListFinder.FindUserByUsername(users, username) != null) {
            return FetchTimeline(username, 0);
        }
        return new ArrayList<Tweet>();
    }

    @GET
    @Path("/{username}/fetchtimeline")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Get specific user timeline
     * @param username
     * @param count
     * @return
     */
    public List<Tweet> FetchTimeline(@PathParam("username") String username, @QueryParam("count") int count) throws IOException, JSONException, ParseException {
        return RestTwitterApi.FetchTimeline(username, count);
    }

    @GET
    @Path("/{username}/bookmarks")
    @Produces(MediaType.APPLICATION_JSON)
    /**
     * Get user bookmarks
     * @param username
     * @return
     */
    public List<Tweet> GetBookmarks(@PathParam("username") String username) {
        return JsonFile.ReadBookmarks(username);
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
