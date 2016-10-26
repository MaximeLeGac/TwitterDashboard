<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-Type" content="text/html">
    
    <title>Twitter Dashboard</title>
    
    <link href="WEB-INF/css/dashboard.css" type="text/css" rel="stylesheet" />
</head>
    <body>
        <table>
            <tr class="timeline">
                <td><h1>Timeline</h1></td>
            </tr>
            <tr>
                <td>
                    <form action="ControllerTimeline" method="post">  
                        <input class="inputUser" type="text" name="username">
                        <input type="submit" value="search">  
                    </form>
                </td>
            </tr>
        </table>
        
        
        
        
    </body>
</html>
