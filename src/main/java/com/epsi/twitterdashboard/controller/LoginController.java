/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import com.epsi.twitterdashboard.model.Tweet;
import com.epsi.twitterdashboard.service.RestController;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import twitter4j.JSONException;

public class LoginController extends HttpServlet {
    
    private static final String Server_Url = "http://epsi-i4-twitterdashboard.cleverapps.io/";
    private static final String Local_Url = "http://localhost:8080/";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");
        
        String username = request.getParameter("user");
        List<Tweet> listTweets = null;
        RestController restContr = new RestController();
        try {
            listTweets = restContr.Login(username);
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listTweets != null) {
            request.setAttribute("listTweets", listTweets);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);
        } 
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {  
        response.setContentType("text/html");
        
        String username = request.getParameter("user");
        List<Tweet> listTweets = null;
        RestController restContr = new RestController();
        try {
            listTweets = restContr.Login(username);
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (listTweets != null) {
            request.setAttribute("listTweets", listTweets);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);
        } 
    }
}
