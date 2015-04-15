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
  String userEmail = request.getUserPrincipal().getName();
  int aid = album.getAID(userEmail, album_name);
%>

<html>
<head><title>Album <%=album_name %></title></head>

<body>

<a href="/photoshare/index.jsp">Return to main page</a>
<p>Click here to <a href="/photoshare/index.jsp" onclick=<% %> >Delete Album</a></p>

<h2><%=album_name %> Pictures</h2>
<table>
    <tr>
        <%
            PictureDao pictures = new PictureDao();
            List<Integer> pictureIds = pictures.albumPictureIds(aid);
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/img?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a>
        </td>
        <%
            }
        %>
    </tr>
</table>

</body>
</html>
