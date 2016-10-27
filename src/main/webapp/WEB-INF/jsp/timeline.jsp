<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.epsi.twitterdashboard.model.Tweet"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Twitter Dashboard</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <h1>Timeline</h1>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
    <table>
        <form action="ControllerTimeline" method="post">

            <%
                String uname = (String) request.getAttribute("username");
                out.print("<h1>" + uname + "</h1>");
            %>

            <input type="text" name="username">
            <input type="submit" value="search">

            <table>
                <tr>
                    <td>
                        <%
                            List<Tweet> listTweets = (List) request.getAttribute("listTweets");

                            if (!(listTweets == null || listTweets.isEmpty())) {
                                for (Tweet tweet : listTweets) {
                                    out.print(tweet.getBody());
                                    out.print("</td><td>");
                                    //out.print("<input type=\"button\" value=\"Ajouter Bookmark\" onClick=\"ajoutBook(" + tweet.getId() + ")\">");
                                    //out.print("<input type=\"button\" value=\"Supprimer Bookmark\" onClick=\"suppBook(" + tweet.getId() + ")\">");
                                    out.print("<br>Ajouter Bookmark <input type=\"checkbox\" name=\"ADD\" value=\"" + tweet.getId() + "\">");
                                    out.print("Supprimer Bookmark <input type=\"checkbox\" name=\"DEL\" value=\"" + tweet.getId() + "\">");
                                    
                                    out.print("</td></tr><tr><td>");
                                }
                            }

                        %>
                    </td>
                </tr>
            </table>

            <br><br>
            <input type="submit" value="bookmarks">
        </form>
    
  </body>
</html>