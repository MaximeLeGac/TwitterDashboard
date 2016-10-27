/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

public class SubscribeController extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");
        
        try {
            
            String username = request.getParameter("user");
            String password = request.getParameter("mdp");
            
            String url = "jdbc:mysql://localhost/phpmyadmin";
            String user = "root";
            String passwd = "";
            
            Connection connection = DriverManager.getConnection(url, user, passwd); 
            PreparedStatement insertNew = connection.prepareStatement("INSERT INTO utilisateur(name, password) VALUES (?,?)");
            insertNew.setString(1, username);             //INT   field type
            insertNew.setString(2, password);  //OTHER field type
            insertNew.executeUpdate();
            
            connection.commit();
            connection.close();
            
            RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
            rd.forward(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(SubscribeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)   throws ServletException, IOException {  
        doPost(req, resp);  
    }
    
}
