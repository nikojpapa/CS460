<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>

<%
  TagDao tag = new TagDao();
  String tag_name = request.getParameter("tag_name");
  String url = "tags.jsp?tag_name=" + tag_name;
  String userEmail = request.getUserPrincipal().getName();
  int tid = tag.getTID(tag_name);
%>

<html>
<head><title>Tag <%=tag_name %></title></head>

<body>

<a href="/photoshare/index.jsp">Return to main page</a>
<form id="delete_form" action="tags.jsp" method="post">
  <input type="hidden" name="tag_name" value=<%=tag_name %> />
  <input type="hidden" name="deleted" value="true"/>
  <p>Click here to <input type="submit" value="Delete Tag"/></p>
</form>

<h2 id="page_head"><%=tag_name %> Pictures</h2>
<table id="pics">
    <tr>
        <%
            PictureDao pictures = new PictureDao();
            String pid = request.getParameter("delete_pic");
            if (!(pid == null)) {
              pictures.delete(Integer.parseInt(pid));
            }
            List<Integer> pictureIds = pictures.tagPictureIds(tag_name);
            for (Integer pictureId : pictureIds) {
        %>
        <td><a href="/photoshare/img?picture_id=<%= pictureId %>">
            <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a><br>
        <form action="tags.jsp" method="post">
          <input type="hidden" name="tag_name" value=<%=tag_name %> />
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
  <% tag.deleteTag(tid); %>
  <script>
  document.getElementById("delete_form").remove();
  document.getElementById("pics").remove();
  document.getElementById("page_head").innerText = "Tag Deleted";
  </script>
<% } %>

</body>
</html>
