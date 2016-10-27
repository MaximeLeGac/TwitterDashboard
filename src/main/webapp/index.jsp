<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Twitter Dashboard</title>

  </head>
  <body>
    <h1>Timeline</h1>
    
    <form action="ControllerTimeline" method="get">
        <input type="text" name="username">
        <input type="submit" value="search">  
    </form>
    
  </body>
</html>