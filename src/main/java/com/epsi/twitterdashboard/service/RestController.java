package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.twitter4j.RestTwitterApi;
import java.util.List;
import javax.ws.rs.Consumes;
import twitter4j.TwitterException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;

/**
 * @author Allan
 */
@Path("rest")
public class RestController {
    
    @GET
    @Path("/tweet/{id}")
    @Produces("application/json")
    /**
     * Get tweet corresponding to the id 
     */
    public Tweet GetTweet(@PathParam("id") int id) {
        return new Tweet(Integer.toString(id));
    }

    @GET
    @Path("/timeline")
    @Produces("application/json")
    /**
     * Get current user timeline
     */
    public List GetTimeline() throws TwitterException {
        return RestTwitterApi.GetInstance().getHomeTimeline();
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
