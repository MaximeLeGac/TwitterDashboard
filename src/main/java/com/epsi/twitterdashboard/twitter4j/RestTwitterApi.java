package com.epsi.twitterdashboard.twitter4j;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.parser.TwitterParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;  
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.codec.binary.Base64;
import twitter4j.JSONObject;
import twitter4j.JSONException;

/**
 * Classe de configuration de l'API Twitter4j
 * @author Allan
 */
public class RestTwitterApi {
    
    public static String ConsumerKey = "RAfoVr8OZR6X1UxkToVbRD5zI";
    public static String ConsumerSecret = "KMQexOiK25A1g3R0ZbDFPZi4PMnjNkG7hw7qvUM9ZKClHyW4N6";
    public static String TwitterAppName = "epsi-twitterdashboard";
    public static String BearerToken = null;
    
    /**
     * Fetches the first tweets from a given user's timeline
     * @param username
     * @param numberLimit
     * @return
     * @throws IOException 
     * @throws JSONException 
     * @throws ParseException 
     */
    public static List<Tweet> FetchTimeline(String username, int numberLimit) throws IOException, JSONException, ParseException {
        HttpsURLConnection connection = null;
        String tweets = "";
        try {
            URL url = new URL("https://api.twitter.com/1.1/statuses/home_timeline.json?screen_name=" + username + (numberLimit > 0 ? "&count=" + numberLimit : "")); 
            connection = (HttpsURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("GET"); 
            connection.setRequestProperty("Host", "api.twitter.com");
            connection.setRequestProperty("User-Agent", RestTwitterApi.TwitterAppName);
            connection.setRequestProperty("Authorization", "Bearer " + GetBearerToken());
            connection.setUseCaches(false);
            tweets = ReadResponse(connection);
        }
        catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return TwitterParser.ParseDatabase(username, tweets);
    }
    
    /**
     * Requests and returns a bearer token from twitter
     * @return bearer token
     * @throws IOException
     * @throws JSONException 
     */
    private static String GetBearerToken() throws IOException, JSONException {
        if (RestTwitterApi.BearerToken == null) {
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
                RestTwitterApi.BearerToken = ((tokenType.equals("bearer")) && (token != null)) ? token : null;
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
        return RestTwitterApi.BearerToken;
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
