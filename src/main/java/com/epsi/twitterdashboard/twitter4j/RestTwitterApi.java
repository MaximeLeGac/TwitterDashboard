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
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import twitter4j.JSONObject;
import twitter4j.JSONException;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Classe de configuration de l'API Twitter4j
 * @author Allan
 */
public class RestTwitterApi {
    
    public static String ConsumerKey = "RAfoVr8OZR6X1UxkToVbRD5zI";
    public static String ConsumerSecret = "KMQexOiK25A1g3R0ZbDFPZi4PMnjNkG7hw7qvUM9ZKClHyW4N6";
    public static String TwitterAppName = "epsi-twitterdashboard";
    
    public static String VanbleusaToken = "fImVTJxSFYBPOt1t4G7kVUq0mUYIcDpORykUo6Qx";
    public static String VanbleusaTokenSecret = "O2ethhUa0rNkUf9sxKAP10XvDgEaJr3LsUJiUHYS0ktdu";
    
    /**
     * Twitter Instance
     */
    private static Twitter Instance = null;
    
    
    /**
     * Handle TwitterApi instance singleton
     * @return Twitter Instance
     */
    public static Twitter GetInstance() {
        if (RestTwitterApi.Instance == null) {
            RestTwitterApi.InitInstance();
        }
        return RestTwitterApi.Instance;
    }

    /**
     * Initialise Twitter Instance
     */
    private static void InitInstance() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(RestTwitterApi.ConsumerKey)
            .setOAuthConsumerSecret(RestTwitterApi.ConsumerSecret)
            .setOAuthAccessToken(RestTwitterApi.VanbleusaToken)
            .setOAuthAccessTokenSecret(RestTwitterApi.VanbleusaTokenSecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        RestTwitterApi.Instance = tf.getInstance();
    }
    
    /**
     * Fetches the first tweet from a given user's timeline
     * @return
     * @throws IOException 
     * @throws JSONException 
     */
    public static String FetchTimeline() throws IOException, JSONException {
        HttpsURLConnection connection = null;
        try {
            URL url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=%3Cusername%3E&count=50"); 
            connection = (HttpsURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("GET"); 
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", RestTwitterApi.TwitterAppName);
            connection.setRequestProperty("Authorization", "Bearer " + GetBearerToken());
            connection.setUseCaches(false);
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
     * Get authorization key from current parameters
     * @return authorization key
     * @throws UnsupportedEncodingException
     */
    private static String GetAuthorizationKey() throws UnsupportedEncodingException {
        String encodedConsumerKey = URLEncoder.encode(RestTwitterApi.ConsumerKey, "UTF-8");
        String encodedConsumerSecret = URLEncoder.encode(RestTwitterApi.ConsumerSecret, "UTF-8");
        String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
        byte[] encodedBytes = Base64.encodeBase64(fullKey.getBytes());
        return new String(encodedBytes); 
    }
    
    /**
     * Requests and returns a bearer token from twitter
     * @return bearer token
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
            connection.setRequestProperty("Authorization", "Basic " + GetAuthorizationKey());
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
     * Allows to write a http request
     * @param connection
     * @param textBody
     * @return true if the writing went well
     * @throws IOException
     */
    private static void WriteRequest(HttpsURLConnection connection, String textBody) throws IOException {
        OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
        BufferedWriter wr = new BufferedWriter(osw);
        wr.write(textBody);
        wr.flush();
    }

    /**
     * Allows to read a http response
     * @param connection
     * @return the response as string
     * @throws IOException
     */
    private static String ReadResponse(HttpsURLConnection connection) throws IOException {
        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        StringBuilder str = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            str.append(line).append(System.getProperty("line.separator"));
        }
        return str.toString();
    }
}
