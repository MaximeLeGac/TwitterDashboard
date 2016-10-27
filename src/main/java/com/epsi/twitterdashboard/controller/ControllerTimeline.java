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

/**
 *
 * @author MLG
 */
public class ControllerTimeline extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");  
        //<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>  

        RestController restContr = new RestController();
        List<Tweet> listTweets = new ArrayList<Tweet>();
        String username = "";
        URL url = null;
        HttpURLConnection connection = null;
        String tweets = "";
        
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");  

            request.setAttribute("username", username);
            url = new URL("http://localhost:8080/dash/rest/fetchtimeline/" + username + "&count=" + 20);
            connection = (HttpURLConnection) url.openConnection();
            tweets = ReadResponse(connection);
            try {
                listTweets = TwitterParser.ParseTweets(tweets);
            } catch (JSONException ex) {
                Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (listTweets != null && !listTweets.isEmpty()) {
                request.setAttribute("listTweets", listTweets);
            }
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);
        } else {
            username = (String) request.getAttribute("username");
            listTweets = (List<Tweet>) request.getAttribute("listTweets");
            
            for (int i = 0; i < listTweets.size(); i++) {
                String[] tabADD = request.getParameterValues("ADD");
                String[] tabDEL = request.getParameterValues("DEL");
                
                for (int j=0; j<tabADD.length; j++) {
                    restContr.Bookmark(username, Integer.parseInt(tabADD[j]));
                    url = new URL("http://localhost:8080/dash/rest/" + username + "/bookmark/" + Integer.parseInt(tabADD[j])); 
                    connection = (HttpURLConnection) url.openConnection();
                    ReadResponse(connection);
                }
                for (int j=0; j<tabDEL.length; j++) {
                    url = new URL("http://localhost:8080/dash/rest/" + username + "/deletebookmark/" + Integer.parseInt(tabDEL[j])); 
                    connection = (HttpURLConnection) url.openConnection();
                    ReadResponse(connection);
                }
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response); 
        }
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)   throws ServletException, IOException {  
        doPost(req, resp);  
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
