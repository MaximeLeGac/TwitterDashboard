<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.epsi.twitterdashboard.model.Tweet"%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html">
        <title>Twitter Dashboard</title>
    </head>
    
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
                                out.print("<br>Pas de tweet à consulter.");
                            }
                            
                        %>
                    </td>
                </tr>
            </form>
        </table>
    </body>
</html>
