package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import java.io.IOException;
import javax.ws.rs.Consumes;
import twitter4j.TwitterException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
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
    public String FetchTimeline(@PathParam("username") String username) throws TwitterException, IOException, JSONException {
        return RestTwitterApi.FetchTimeline(username);
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
