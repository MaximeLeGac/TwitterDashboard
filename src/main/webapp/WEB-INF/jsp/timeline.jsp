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

            <%
                String uname = (String) request.getAttribute("username");
                out.print("<h1>" + uname + "</h1>");
                
                List<Tweet> listTweets = (List) request.getAttribute("listTweets");

                Iterator it = listTweets.iterator();
                while(it.hasNext()) {
                  out.print("<br>Tweet : " + ((Tweet) it.next()).getBody());
                }
           %>

            <input type="text" name="username">
            <input type="submit" value="search">

            <table>
                <tr>
                    <td>
                    </td>
                </tr>
            </table>

        </form>
    
  </body>
</html>