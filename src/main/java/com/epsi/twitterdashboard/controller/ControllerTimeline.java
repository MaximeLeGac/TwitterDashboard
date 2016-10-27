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
        List<Tweet> listTweets = new ArrayList<Tweet>();
        String[] tabADD = null;
        String[] tabDEL = null;
        String username = "";
        HttpURLConnection connection = null;
        String tweets = "";
        
        username = request.getParameter("username");
        request.setAttribute("username", username);
        
        try {
            URL url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/bookmark&count=" + 20);
            connection = (HttpURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            /*OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write("message=" + message);
            writer.close();*/
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
        
        try {
            listTweets = TwitterParser.ParseTweets(tweets);
        } catch (JSONException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (listTweets != null && !listTweets.isEmpty()) {
            request.setAttribute("listTweets", listTweets);
        }
        
        
        /*try {
            //url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + username + "/fetchtimeline&count=" + 20);
            //connection = (HttpURLConnection) url.openConnection();
            //tweets = ReadResponse(connection);
            
            try {
                listTweets = TwitterParser.ParseTweets(tweets);
            } catch (JSONException ex) {
                Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (listTweets != null && !listTweets.isEmpty()) {
                request.setAttribute("listTweets", listTweets);
            }
        
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

        } catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }*/
        
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
        rd.forward(request, response); 
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Tweet> listTweets = new ArrayList<Tweet>();
        HttpURLConnection connection = null;
        String tweets = "";
        
        try {
            URL url = new URL(ControllerTimeline.Local_Url + "dash/rest/" + request.getParameter("username") + "/fetchtimeline&count=" + 20);
            connection = (HttpURLConnection) url.openConnection();           
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            tweets = ReadResponse(connection);
            listTweets.addAll(TwitterParser.ParseTweets(tweets));
            request.setAttribute("listTweets", listTweets);
        }
        catch (MalformedURLException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        
        response.setContentType("text/html");
        RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
        rd.forward(request, response); 
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
