<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Photoshare Login</title></head>

<body>
<h2>Please login</h2>

<form method="POST" action="j_security_check">
    <table>
        <tr><th>Email</th><td><input type="text" name="j_username"></td></tr>
        <tr><th>Password</th><td><input type="password" name="j_password"></td>
        </tr>
        <tr><td colspan="2" align="right"><input type="submit" value="Login"/>
        </td></tr>
    </table>
</form>
<a href='/photoshare/newuser.jsp'>Register</a>

<h2>Existing pictures</h2>
<table>
    <tr>
        <%
	        PictureDao pictureDao = new PictureDao();
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