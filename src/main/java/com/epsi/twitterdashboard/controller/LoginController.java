/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import com.epsi.twitterdashboard.service.RestController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
        String res = "";
        RestController restContr = new RestController();
        try {
            res = restContr.Login(username);
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!"".equals(res)) {
            request.setAttribute("listTweets", res);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);
        }
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {  
        response.setContentType("text/html");
        
        String username = request.getParameter("user");
        String res = "";
        RestController restContr = new RestController();
        try {
            res = restContr.Login(username);
        } catch (JSONException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!"".equals(res)) {
            request.setAttribute("listTweets", res);
            RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp");
            rd.forward(request, response);
        } 
    }
}
