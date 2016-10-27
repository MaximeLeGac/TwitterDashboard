<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.epsi.twitterdashboard.model.Tweet"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Twitter Dashboard</title>
  </head>
  <body>
    <h1>Timeline</h1>
    
    <table>
            <form action="ControllerTimeline" method="post">
                <tr class="timeline">
                    
                    <td>
                    <%
                        String uname = (String) request.getAttribute("username");
                        out.print("<h1>Timeline " + uname + "</h1>");
                    %>
                    </td>
                </tr>
                <tr>
                    <td>
                            <input class="inputUser" type="text" name="username">
                            <input type="submit" value="search">  

                    </td>
                </tr>
                <tr>
                    <td>
                        <%
                            List<Tweet> listTweets = (List) request.getAttribute("listTweets");
                            
                            if (!(listTweets == null || listTweets.isEmpty())) {
                                for (Tweet tweet : listTweets) {
                                    out.print("<br>Tweet : " + tweet.getBody());
                                }
                            } else {
                                out.print("<br>Pas de tweet � consulter.");
                            }
                            
                        %>
                    </td>
                </tr>
            </form>
        </table>
    
    </body>
</html>