<%--
  Author: Nicholas Papadopoulos <npapa@bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.PictureDao" %>

<%
int pid = Integer.parseInt(request.getParameter("pid"));
PictureDao picture = new PictureDao();
%>


<html>
  <head>
    <meta name="viewport" content="width=device-width, minimum-scale=0.1">
    <title>Picture </title>
    <style type="text/css"></style>
  </head>

  <body>
    <img style="-webkit-user-select: none" src="http://localhost:9545/photoshare/img?picture_id=<%=pid %>">

    <br>
    <%= picture.listTags(pid) %>

  </body>
</html>