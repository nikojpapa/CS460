<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewFriendDao" %>
<jsp:useBean id="newFriendBean"
             class="photoshare.NewFriendBean" />
<jsp:setProperty name="newFriendBean" property="*"/>

<html>
<head><title>Friend List</title></head>

<body>
<!-- We want to show the form unless we successfully create a new user -->
<% boolean addedFriend = false;
   String err = null; 
   String userEmail = request.getUserPrincipal().getName();
   String first_name = "";
   String last_name = "";

   NewFriendDao newFriendDao = new NewFriendDao();
   
   if (!newFriendBean.getEmail().equals("")) {

     // Try to add friend
     String friendEmail = newFriendBean.getEmail();
     boolean success = newFriendDao.create(friendEmail, userEmail );
     if (success) {
      addedFriend = true;
      first_name = newFriendDao.getName(friendEmail, "first_name");
      last_name = newFriendDao.getName(friendEmail, "last_name");
     } else {
       err = "Couldn't add friend (that friend may already be added)";
     }
   }

   String friend_list = newFriendDao.getFriendList(userEmail);
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font><br><br>
<% } else if (addedFriend) { %>

<h2>Success!</h2>

<%= first_name + " " + last_name %> has been added to your friends list.<br><br>

<% } %>

<a href="/photoshare/index.jsp">Return to main page</a>

<h2>Add Friend</h2>
<form action="friendList.jsp" method="post">
  Email: <input type="text" name="email"/><br>
  <input type="submit" value="Add"/><br>
</form>

<h2>Friend List</h2>

<%= friend_list %>

</body>
</html>
