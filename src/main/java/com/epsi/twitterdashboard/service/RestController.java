package com.epsi.twitterdashboard.service;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.twitter4j.TwitterApi;
import java.util.List;
import javax.servlet.jsp.PageContext;
import org.json.JSONObject;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

/**
 * @author Alexandre
 * REST Examples => https://www.mkyong.com/webservices/jax-rs/resteasy-hello-world-example/
 */
public class RestController {

    @GET
    @Path("/{param}")
    /**
     * Méthode de test pour un appel de service REST avec RESTeasy
     * Accessible via : http://epsi-i4-twitterdashboard.cleverapps.io/{param}
     */
    public Response printMessage(@PathParam("param") String msg) {
        // Utilisation des codes de retour http (SC_OK = 200)
        return Response.status(javax.servlet.http.HttpServletResponse.SC_OK)
                .entity("Server received param : " + msg)
                .build();
    }

    @GET
    @Path("/get")
    /**
     * Méthode de test pour un appel de service REST avec RESTeasy
     * Renvoi la référence d'un objet Java
     * Accessible via : http://epsi-i4-twitterdashboard.cleverapps.io/get
     */
    public Tweet obj() {
        return new Tweet("test new tweet");
    }

    @GET
    @Path("/getJson")
    /**
     * Méthode de test pour un appel de service REST avec RESTeasy
     * Renvoi un objet Java parsé en JSON => /!\ Les getters sont requis pour parser les propriétés
     * Accessible via : http://epsi-i4-twitterdashboard.cleverapps.io/rest/getJSon
     */
    public String objJson() {
        return new JSONObject(new Tweet("test new tweet")).toString();
    }

    @GET
    @Path("/getJson/{body}")
    /**
     * Méthode de test pour un appel de service REST avec RESTeasy
     * Renvoi un objet Java parsé en JSON => /!\ Les getters sont requis pour parser les propriétés
     * Accessible via : http://epsi-i4-twitterdashboard.cleverapps.io/rest/getJSon/{body}
     */
    public String objJsonWithBody(@PathParam("body") String body) {
        return new JSONObject(new Tweet(body)).toString();
    }

    @GET
    @Path("/timeline")
    /**
     * Renvoi la timeline du compte configuré dans TwitterApi
     */
    public List GetTimeline() throws TwitterException {
        return TwitterApi.GetInstance().getHomeTimeline();
    }
    
    /*@GET
    @Path("/get")
    @Produces("application/json")
    public Product getProductInJSON() {
        return new Product("iPad 3", 999);
    }*/
    
    /*@POST
    @Path("/post")
    @Consumes("application/json")
    public Response createProductInJSON(Product product) {
        return Response.status(201).entity("Product created : " + product).build();
    }*/
}
