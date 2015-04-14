<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<jsp:useBean id="newUserBean"
             class="photoshare.NewUserBean" />
<jsp:setProperty name="newUserBean" property="*"/>

<html>
<head><title>New User</title></head>

<body>
<!-- We want to show the form unless we successfully create a new user -->
<% boolean showForm = true;
   String err = null; %>

<% if (!newUserBean.getEmail().equals("")) {
     if (!newUserBean.getPassword1().equals(newUserBean.getPassword2())) {
       err = "Both password strings must match";


     }
     else if (newUserBean.getPassword1().length() < 4) {
       err = "Your password must be at least four characters long";
     }
     else if (newUserBean.getEmail().equals("")) {
       err = "You must enter your date of birth";
     }
     else {
       // We have valid inputs, try to create the user
       NewUserDao newUserDao = new NewUserDao();

       boolean success = newUserDao.create(newUserBean.getFirst(), newUserBean.getLast(), newUserBean.getEmail(), newUserBean.getDOB(), newUserBean.getPassword1(), newUserBean.getGender(), newUserBean.getHcity(), newUserBean.getHstate(), newUserBean.getHcountry(), newUserBean.getCcity(), newUserBean.getCstate(), newUserBean.getCcountry(), newUserBean.getHname(), newUserBean.getHgrad(), newUserBean.getCname(), newUserBean.getCgrad() );
       if (success) {
         showForm = false;
       } else {
         err = "Couldn't create user (that email may already be in use)";
       }
     }
   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<% } %>

<% if (showForm) { %>

<h2>New user info</h2>

<form action="newuser.jsp" method="post">
  **Required**<br>
  First Name: <input type="text" name="first"/><br>
  Last Name: <input type="text" name="last"/><br>
  Email: <input type="text" name="email"/><br>
  Date of Birth: <input type="text" name="dob"/><br>
  Password: <input type="password" name="password1"/><br>
  Re-enter password: <input type="password" name="password2"/><br>
  <br>
  **Optional**<br>
  Gender: <input type="text" name="gender"/><br>
  <br>
  --Hometown--<br>
  City: <input type="text" name="h_city"/><br>
  State: <input type="text" name="h_state"/><br>
  Country: <input type="text" name="h_country"/><br>
  <br>
  --Current Location--<br>
  City: <input type="text" name="c_city"/><br>
  State: <input type="text" name="c_state"/><br>
  Country: <input type="text" name="c_country"/><br>
  <br>
  --Education--<br>
  Highschool Name: <input type="text" name="h_name"/><br>
  Highschool Graduation Date: <input type="text" name="h_grad"/><br>
  <br>
  College Name: <input type="text" name="c_name"/><br>
  College Graduation Date: <input type="text" name="c_grad"/><br>
  <input type="submit" value="Create"/><br/>
</form>

<% }
   else { %>

<h2>Success!</h2>

<p>A new user has been created with email <%= newUserBean.getEmail() %>.
You can now return to the <a href="login.jsp">login page</a>.

<% } %>

</body>
</html>
