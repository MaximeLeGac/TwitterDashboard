/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.parser.TwitterParser;
import com.epsi.twitterdashboard.service.RestController;
import java.io.BufferedReader;
import java.io.IOException;  
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import twitter4j.JSONException;

public class ControllerTimeline extends HttpServlet {
    
    private static final String Server_Url = "http://epsi-i4-twitterdashboard.cleverapps.io/";
    private static final String Local_Url = "http://localhost:8080/";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");

        RestController restContr = new RestController();
        List<Tweet> listTweets = new ArrayList<Tweet>();
        String[] tabADD = null;
        String[] tabDEL = null;
        String username = "";
        URL url = null;
        HttpURLConnection connection = null;
        String tweets = "";
        
        username = request.getParameter("username");  
        
        request.setAttribute("username", username);
        
        try {
            url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/fetchtimeline&count=" + 20);
            connection = (HttpURLConnection) url.openConnection();
            //tweets = ReadResponse(connection);
            
            try {
                listTweets = TwitterParser.ParseTweets(tweets);
            } catch (JSONException ex) {
                Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (listTweets != null && !listTweets.isEmpty()) {
                request.setAttribute("listTweets", listTweets);
            }
        
/*
            if (request.getParameter("ADD") != null) {
                tabADD = request.getParameterValues("ADD");
            }
            if (request.getParameter("ADD") != null) {
                tabDEL = request.getParameterValues("DEL");
            }

            if (tabADD != null && tabADD.length != 0) {
                for (int j=0; j<tabADD.length; j++) {
                    restContr.Bookmark(username, Integer.parseInt(tabADD[j]));
                    url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/bookmark/" + Integer.parseInt(tabADD[j])); 
                    connection = (HttpURLConnection) url.openConnection();
                    ReadResponse(connection);
                }
            }

            if (tabDEL != null && tabDEL.length != 0) {
                for (int j=0; j<tabDEL.length; j++) {
                    url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/deletebookmark/" + Integer.parseInt(tabDEL[j])); 
                    connection = (HttpURLConnection) url.openConnection();
                    ReadResponse(connection);
                }
            }
*/

        } catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
        rd.forward(request, response); 
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> listTweets = new ArrayList<Tweet>();
        String username = request.getParameter("username"); 
        request.setAttribute("username", username);
        
        URL url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/fetchtimeline&count=" + 20);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        try {
            listTweets.addAll(TwitterParser.ParseTweets(ReadResponse(connection)));
        } catch (JSONException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!listTweets.isEmpty()) {
            request.setAttribute("listTweets", listTweets);
        }
    }
    
    /**
     * Allows to read a http response
     * @param connection
     * @return the response as string
     * @throws IOException
     */
    private static String ReadResponse(HttpURLConnection connection) throws IOException {
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
