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
CommentDao comment = new CommentDao();
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

    <form id="like_pic" action="picture.jsp" method="post">
      <input type="hidden" name="pid" value=<%=pid %> ></input>
      <input type="hidden" name="user" value=<%=userEmail %> ></input>
      <input type="hidden" name="comment_text" value = "like"/>
      <input type="submit" value="Like This Picture"> </input>
    </form>

    <a id="num_likes" href='javascript:void(0);' onclick="
      var div = document.getElementById('likes');
      if (div.style.display !== 'none') {
          div.style.display = 'none';
      }
      else {
          div.style.display = 'block';
      }"></a> Likes
    <table id='likes'></table>

    <form id="leave_comment" action="picture.jsp" method="post">
      <input type="hidden" name="pid" value=<%=pid %> ></input>
      <input type="hidden" name="user" value=<%=userEmail %> ></input>
      <p>Leave Comment</p>
      <p><input type="text" name="comment_text"/></p>
      <input type="submit" value="Submit"> </input>
    </form>

    <br><br>
    <%
    if (new_comm != null) {
      comment.addComment(new_comm, new Date().toLocaleString(), userEmail, pid);
    }

    String comments = comment.listComments(pid);

    String likes_list = comment.listLikes(pid);
    int num_likes = 0;
    if (!likes_list.equals("")) {
      num_likes = Integer.parseInt(likes_list.substring(likes_list.length() - 1));

      likes_list = likes_list.substring(0, likes_list.length()-1);
    };
    %>
    <%="<script>document.getElementById('num_likes').text = '" + num_likes + "'; document.getElementById('likes').innerHTML = '" + likes_list.replace("'", "\\'") + "'; document.getElementById('likes').style.display = 'none';</script>"
    %>

    <%= comments %>

  </body>
</html>