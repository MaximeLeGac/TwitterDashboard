/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.twitter4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;  
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import twitter4j.JSONObject;
import twitter4j.JSONException;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Classe de test de l'API Twitter4j
 * @author Allan
 */
public class TwitterApi {
    
    /**
     * Authentifie l'utilisateur sur Twitter
     * @return L'instance Twitter initialisé avec l'utilisateur authentifié
     */
    public static Twitter Authenticate() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        // Get OAuth from Twitter Application Registration
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey("*********************")
          .setOAuthConsumerSecret("******************************************")
          .setOAuthAccessToken("**************************************************")
          .setOAuthAccessTokenSecret("******************************************");   
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
    
    /**
     * Renvoi la clé d'authentification à partir des paramètres d'entrée
     * @param consumerKey Clé de l'utilisateur
     * @param consumerSecret Secret de l'utilisateur
     * @return la clé d'authentification
     */
    private static String GetAuthenticationKey(String consumerKey, String consumerSecret) throws UnsupportedEncodingException {
        // Encodes the consumer key and secret to create the basic authorization key
        String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
        String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
        String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
        byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
        return new String(encodedBytes); 
    }
    
    /**
     * Constructs the request for requesting a bearer token and returns that token as a string
     * @return
     * @throws IOException
     * @throws JSONException 
     */
    private static String GetBearerToken() throws IOException, JSONException {
	HttpsURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/oauth2/token"); 
            connection = (HttpsURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("POST"); 
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Twitter Dashboard");
            connection.setRequestProperty("Authorization", "Basic " + GetAuthenticationKey("vanbleusa","Acdpm11*3sdoi"));
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8"); 
            connection.setRequestProperty("Content-Length", "29");
            connection.setUseCaches(false);
            WriteRequest(connection, "grant_type=client_credentials");

            // Parse the JSON response into a JSON mapped object to fetch fields from.
            JSONObject obj = new JSONObject(ReadResponse(connection));
            String tokenType = (String)obj.get("token_type");
            String token = (String)obj.get("access_token");
            return ((tokenType.equals("bearer")) && (token != null)) ? token : "";
	}
	catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
	}
	finally {
            if (connection != null) {
                connection.disconnect();
            }
	}
    }
    /**
     * 
     * @param connection
     * @param textBody
     * @return 
     */
    private static boolean WriteRequest(HttpsURLConnection connection, String textBody) throws IOException {
        BufferedWriter wr = new BufferedWriter(
            new OutputStreamWriter(connection.getOutputStream()));
        wr.write(textBody);
        wr.flush();
        wr.close();
        return true;
    }

    /**
     * 
     * @param connection
     * @return 
     */
    private static String ReadResponse(HttpsURLConnection connection) throws IOException {
        StringBuilder str = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line = "";
        while((line = br.readLine()) != null) {
            str.append(line).append(System.getProperty("line.separator"));
        }
        return str.toString();
    }
    
    /**
     * Fetches the first tweet from a given user's timeline
     * @return
     * @throws IOException 
     */
    public static String FetchTimelineTweet() throws IOException, JSONException {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=%3Cusername%3E&count=50"); 
            connection = (HttpsURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("GET"); 
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", "Your Program Name");
            connection.setRequestProperty("Authorization", "Bearer " + GetBearerToken());
            connection.setUseCaches(false);
            
            // Parse the JSON response into a JSON mapped object to fetch fields from.
            JSONObject obj = new JSONObject(ReadResponse(connection));
            String tweet = ((JSONObject)obj.get("0")).get("text").toString();
            return (tweet != null) ? tweet : "";
        }
        catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
    /**
     * Retourne la timeline de l'instance Twitter en entrée
     * @param twitter
     * @return
     * @throws TwitterException 
     */
    public static List getTimeline(Twitter twitter) throws TwitterException {
        return twitter.getHomeTimeline();
    }
}
