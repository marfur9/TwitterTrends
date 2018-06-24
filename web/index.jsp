<%--
  Created by IntelliJ IDEA.
  User: marfu_000
  Date: 19.06.2018
  Time: 22:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Check twitter accounts' trends</title>
  </head>
  <body>
    <form action = "/TrendServlet" method = "GET">
        <label>Twitter handle: @</label><input type="text" name="screenname" id="screenname"/>
        <select name = "range">
            <option value = "200">last 200 tweets</option>
            <option value = "400">last 400 tweets</option>
            <option value = "600">last 600 tweets</option>
        </select>
        <input type="submit" value="Submit" />
    </form>
  </body>
</html>
