/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.service.RestController;
import java.io.IOException;  
import java.io.PrintWriter;  
import java.text.ParseException;
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
import twitter4j.TwitterException;

/**
 *
 * @author MLG
 */
public class ControllerTimeline extends HttpServlet {
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");  
        //<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>  
        String username = request.getParameter("username");  
        
        RestController restContr = new RestController();
        List<Tweet> listTweets = new ArrayList<Tweet>();
        
        try {
            
            request.setAttribute("username", username);
            
            // Recuperation de la liste des tweets de la timeline de username
            listTweets = restContr.FetchTimeline(username, 20);
            
            if (!(listTweets == null || listTweets.isEmpty())) {
                request.setAttribute("listTweets", listTweets);
            }
            
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);  
            
        } catch (JSONException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControllerTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  
            throws ServletException, IOException {  
        doPost(req, resp);  
    }
    
    
}
