package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import java.io.IOException;
import java.util.List;
import javax.ws.rs.Consumes;
import twitter4j.TwitterException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import twitter4j.JSONException;

/**
 * @author Allan
 */
@Path("rest")
public class RestController {

    @GET
    @Path("/fetchtimeline/{username}")
    @Produces("application/json")
    /**
     * Get specific user timeline
     */
    public List<Tweet> FetchTimeline(@PathParam("username") String username) throws TwitterException, IOException, JSONException {
        return RestTwitterApi.FetchTimeline(username, -1);
    }

    @POST
    @Path("/tweet")
    @Consumes("application/json")
    /**
     * Add a new tweet
     */
    public void PostTweet(Tweet tweet) {
    }
}
