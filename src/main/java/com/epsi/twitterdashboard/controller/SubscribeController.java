/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epsi.twitterdashboard.controller;

import com.epsi.twitterdashboard.model.User;
import com.epsi.twitterdashboard.service.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubscribeController extends HttpServlet {
    
    private static final String Server_Url = "http://epsi-i4-twitterdashboard.cleverapps.io/";
    private static final String Local_Url = "http://localhost:8080/";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html");
        
        URL url = null;
        HttpURLConnection connection = null;
        String rep = "";
        
        String username = request.getParameter("user");
        String password = request.getParameter("mdp");
        String pseudo = request.getParameter("pseudo");
        String ville = request.getParameter("ville");
        
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setScreenName(pseudo);
        user.setLocation(ville);
        
        try {
            url = new URL(SubscribeController.Local_Url + "dash/rest/subscribe");
            connection = (HttpURLConnection) url.openConnection();        
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("POST"); 
            
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            out.writeObject(user);
            out.flush();
            out.close();
            
            
            rep = ReadResponse(connection);

        } catch (MalformedURLException e) {
            throw new IOException("Invalid endpoint URL specified.", e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        
    }
  
    @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)   throws ServletException, IOException {  
        response.setContentType("text/html");
        
        URL url = null;
        HttpURLConnection connection = null;
        String rep = "";
        
        String username = request.getParameter("user");
        String password = request.getParameter("mdp");
        String pseudo = request.getParameter("pseudo");
        String ville = request.getParameter("ville");
        
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        user.setScreenName(pseudo);
        user.setLocation(ville);
        
        RestController restContr = new RestController();
        restContr.Subscribe(user);
        
            /*
            url = new URL(SubscribeController.Local_Url + "dash/rest/subscribe");
            connection = (HttpURLConnection) url.openConnection();  
            
            connection.setDoOutput(true);
            connection.setDoInput(true); 
            connection.setRequestMethod("POST"); 
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-type", "application/x-java-serialized-object");
            
            
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            out.writeObject(user);
            out.flush();
            out.close();
            
            rep = ReadResponse(connection);

            if (connection != null) {
                connection.disconnect();
            }
            */
            
        
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
