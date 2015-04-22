<%--
  Author: Nicholas Papadopoulos <npapa@bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.Rankings" %>
<%@ page import="photoshare.Recommendations" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Photo Sharing</title></head>

<body>
<h1>A photo sharing application for CS460/660 PA2</h1>

<%  
    String userEmail = request.getUserPrincipal().getName(); 
    PictureDao pictureDao = new PictureDao();
    Recommendations rec = new Recommendations();

    String url = request.getRequestURL().toString();
    if (!url.contains("?")) {
        url += "?";
    } else {
        url += "&";
    }
    String current_tags = request.getParameter("rectags");
    List<String> tagList = new ArrayList<String>();
    String suggest_tags = "";
    if (current_tags != null && !current_tags.equals("")) {
        tagList = rec.recTags(current_tags);

        for (String tagName : tagList) {
            suggest_tags += tagName + ", ";
        }
        suggest_tags = ", " + suggest_tags.substring(0, suggest_tags.length()-2);
    }
%>

Hello <b><code><%= userEmail  %></code></b>, click here to
<a href="/photoshare/logout.jsp">log out</a><br><br>
Click here to <a href="/photoshare/friendList.jsp"> show friends list</a>

<h2>Rankings</h2>
<p id="rankings"></p>

<h2>Upload a new picture</h2>

<form action="index.jsp" enctype="multipart/form-data" method="post">
    <p>Filename: <input type="file" name="filename"/></p>
    <p>Album: <input type="text" name="album_name"/></p>
    <p>Tags: <input id="tags" type="text" name="tags"/> <a id="tagLink" href="#" onclick="
        var new_tags = document.getElementById('tags').value;
        document.getElementById('tagLink').setAttribute('href','<%=url %>rectags=' + new_tags);
        ">Recommend Tags</a></p>

    <input type="submit" value="Upload"/><br/>
</form>
<% System.out.println("<script>document.getElementById('tags').value = '" + current_tags + ", " + suggest_tags + "'</script>"); %>
<%="<script>document.getElementById('tags').value = '" + current_tags + suggest_tags + "'</script>" %>

<%
    String err = "";

    try { 
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture);
            String[] tags = picture.getTags().split("(,|, | )");
            int pid = pictureDao.allPicturesIds().get(0);
            TagDao tagDao = new TagDao();
            for (int i = 0; i < tags.length; i++) {
                tagDao.addTag(tags[i], pid);
            }
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }

    if (!err.equals("")) { %>
        <p><font color=red><b>Error: <%= err %></b></font></p>
    <% }
%>
<%
    Rankings rankings = new Rankings();
    String ranks = rankings.getRankings();
%>
<%="<script>document.getElementById('rankings').innerHTML = '" + ranks + "'</script>" %>

<table>
    <th>My Albums</th><th></th><th>My Tags</th><th></th><th>Most Popular Tags</th>
    <tr>
        <td>
            <%
                AlbumDao albums = new AlbumDao();
                String users_albums = albums.listAlbums(userEmail);
            %>
            <%= users_albums %>
        </td><td/><td>
            <%
                TagDao tags = new TagDao();
                String users_tags = tags.listTags(userEmail);
            %>
            <%= users_tags %>
        </td><td/><td>
            <%= tags.mostPopular() %>
        </td>
    </tr>
</table>

<h2>Existing pictures</h2>
<table>
    <tr>
        <%
            List<Integer> pictureIds = pictureDao.allPicturesIds();
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?pid=<%= pictureId %>&user=<%= userEmail %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a>
        </td>
        <%
            }
        %>
    </tr>
</table>

<h2>You-may-also-like</h2>
<table>
    <tr>
        <%
            pictureIds = rec.recPics(userEmail);
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/picture.jsp?pid=<%= pictureId %>&user=<%= userEmail %>">
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
