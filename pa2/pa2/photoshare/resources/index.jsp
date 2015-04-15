<%--
  Author: Nicholas Papadopoulos <npapa@bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.Rankings" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Photo Sharing</title></head>

<body>
<!-- <h1>A skeleton photo sharing application for CS460/660 PA1</h1> -->
<h1>A photo sharing application for CS460/660 PA2</h1>

Hello <b><code><%= request.getUserPrincipal().getName()  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a><br><br>
Click here to <a href="/photoshare/friendList.jsp"> show friends list</a>

<h2>Rankings</h2>
<%
    Rankings rankings = new Rankings();
    String ranks = rankings.getRankings();
%>
<%= ranks %>

<h2>Upload a new picture</h2>

<form action="index.jsp" enctype="multipart/form-data" method="post">
    <p>Filename: <input type="file" name="filename"/></p>
    <p>Album: <input type="text" name="album_name"/></p>
    <input type="submit" value="Upload"/><br/>
</form>

<%
    PictureDao pictureDao = new PictureDao();
    String err = "";
    try { 
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture);
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }

    if (!err.equals("")) { %>
        <p><font color=red><b>Error: <%= err %></b></font></p>
    <% }
%>

<h2>My Albums</h2>
<%
    AlbumDao albums = new AlbumDao();
    String users_albums = albums.listAlbums(request.getUserPrincipal().getName());
%>
<%= users_albums %>

<h2>Existing pictures</h2>
<table>
    <tr>
        <%
            List<Integer> pictureIds = pictureDao.allPicturesIds();
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
