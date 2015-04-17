<%--
  Author: Nicholas Papadopoulos <npapa@bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.CommentDao" %>
<%@page import="java.util.Date" %>

<%
int pid = Integer.parseInt(request.getParameter("pid"));
String userEmail = request.getParameter("user");
String new_comm = request.getParameter("comment_text");
PictureDao picture = new PictureDao();
%>


<html>
  <head>
    <meta name="viewport" content="width=device-width, minimum-scale=0.1">
    <title>Picture </title>
    <style type="text/css"></style>
  </head>

  <body>
    <p><a href="/photoshare/index.jsp">Return to main page</a></p>
    <img style="-webkit-user-select: none" src="http://localhost:9545/photoshare/img?picture_id=<%=pid %>">

    <br>
    <%= picture.listTags(pid) %>

    <h2>Comments</h2>
    <form id="leave_comment" action="picture.jsp" method="post">
      <input type="hidden" name="pid" value=<%=pid %> ></input>
      <input type="hidden" name="user" value=<%=userEmail %> ></input>
      <p>Leave Comment</p>
      <p><input type="text" name="comment_text"/></p>
      <input type="submit" value="Submit"> </input>
    </form>

    <br><br>
    <%
    CommentDao comment = new CommentDao();
    if (new_comm != null) {
      comment.addComment(new_comm, new Date().toLocaleString(), userEmail, pid);
    }

    String comments = comment.listComments(pid);
    %>

    <%= comments %>

  </body>
</html>