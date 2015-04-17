<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewFriendDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>

<%
  AlbumDao album = new AlbumDao();
  String album_name = request.getParameter("album_name");
  String url = "albums.jsp?album_name=" + album_name;
  String userEmail = request.getUserPrincipal().getName();
  int aid = album.getAID(userEmail, album_name);
%>

<html>
<head><title>Album <%=album_name %></title></head>

<body>

<a href="/photoshare/index.jsp">Return to main page</a>
<form id="delete_form" action="albums.jsp" method="post">
  <input type="hidden" name="album_name" value=<%=album_name %> />
  <input type="hidden" name="deleted" value="true"/>
  <p>Click here to <input type="submit" value="Delete Album"/></p>
</form>

<h2 id="page_head"><%=album_name %> Pictures</h2>
<table id="pics">
    <tr>
        <%
            PictureDao pictures = new PictureDao();
            String pid = request.getParameter("delete_pic");
            if (!(pid == null)) {
              pictures.delete(Integer.parseInt(pid));
            }
            List<Integer> pictureIds = pictures.albumPictureIds(aid);
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?pid=<%= pictureId %>&user=<%= userEmail %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a><br>
        <form action="albums.jsp" method="post">
          <input type="hidden" name="album_name" value=<%=album_name %> />
          <input type="hidden" name="deleted" value="false"/>
          <input type="hidden" name="delete_pic" value=<%= pictureId %> />
          <input type="submit" value="Delete Picture"/>
        </form>
        </td>
        <%
            }
        %>
    </tr>
</table>

<% 
if (request.getParameter("deleted").equals("true")) { %>
  <% album.deleteAlbum(aid); %>
  <script>
  document.getElementById("delete_form").remove();
  document.getElementById("pics").remove();
  document.getElementById("page_head").innerText = "Album Deleted";
  </script>
<% } %>

</body>
</html>
