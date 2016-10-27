/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");
        
        try {
            
            String username = request.getParameter("user");
            String password = request.getParameter("mdp");
            
            String url = "jdbc:mysql://localhost/phpmyadmin";
            String user = "root";
            String passwd = "";
            
            Connection connection = DriverManager.getConnection(url, user, passwd);
            
            Statement state = connection.createStatement();
            String query = "SELECT 1 FROM utilisateur WHERE name = " + username + " AND password = " + password;         
            ResultSet res = state.executeQuery(query);
            
            res.close();
            state.close();
            connection.close();
            
            int auth = res.getObject(1, int.class);

            if (auth == 1) {
                RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/jsp/timeline.jsp"); 
                rd.forward(request, response);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)   throws ServletException, IOException {  
        doPost(req, resp);  
    }
}
