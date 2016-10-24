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
 * REST Examples => https://www.mkyong.com/webservices/jax-rs/resteasy-hello-world-example/
 */
@Path("rest")
public class RestController {
    
    @GET
    @Path("/tweet/{id}")
    @Produces("application/json")
    /**
     * Récupère le tweet correspondant à l'id en entrée
     * Local        : http://localhost:8080/TwitterDashboard/dash/rest/tweet/{id}
     * CleverCloud  : http://epsi-i4-twitterdashboard.cleverapps.io/rest/tweet/{id}
     */
    public Tweet GetTweet(@PathParam("id") int id) {
        return new Tweet(Integer.toString(id));
    }

    @POST
    @Path("/tweet")
    @Consumes("application/json")
    /**
     * Ajoute un nouveau tweet
     * Local        : http://localhost:8080/TwitterDashboard/dash/rest/tweet
     * CleverCloud  : http://epsi-i4-twitterdashboard.cleverapps.io/rest/tweet/
     */
    public void PostTweet(Tweet tweet) {
    }

    @GET
    @Path("/timeline")
    @Produces("application/json")
    /**
     * Renvoi la timeline du compte configuré dans TwitterApi
     * Local        : http://localhost:8080/TwitterDashboard/dash/rest/timeline/
     * CleverCloud  : http://epsi-i4-twitterdashboard.cleverapps.io/rest/timeline/
     */
    public List GetTimeline() throws TwitterException {
        return RestTwitterApi.GetInstance().getHomeTimeline();
    }
}
