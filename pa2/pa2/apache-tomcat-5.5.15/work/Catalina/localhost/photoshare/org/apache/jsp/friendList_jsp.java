package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.NewFriendDao;

public final class friendList_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static java.util.List _jspx_dependants;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;


    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\n');
      out.write('\n');
      out.write('\n');
      photoshare.NewFriendBean newFriendBean = null;
      synchronized (_jspx_page_context) {
        newFriendBean = (photoshare.NewFriendBean) _jspx_page_context.getAttribute("newFriendBean", PageContext.PAGE_SCOPE);
        if (newFriendBean == null){
          newFriendBean = new photoshare.NewFriendBean();
          _jspx_page_context.setAttribute("newFriendBean", newFriendBean, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("newFriendBean"), request);
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head><title>Friend List</title></head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("<!-- We want to show the form unless we successfully create a new user -->\n");
 boolean addedFriend = false;
   String err = null; 
   String userEmail = request.getUserPrincipal().getName();
   String first_name = "";
   String last_name = "";
   String search_results = "";

   NewFriendDao newFriendDao = new NewFriendDao();

   System.out.println("first: " + newFriendBean.getFirst());
   
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
   } else if (!newFriendBean.getFirst().equals("") || !newFriendBean.getLast().equals("")) {
      search_results = newFriendDao.simpleSearch(newFriendBean.getFirst(), newFriendBean.getLast());
      System.out.println("SEARCH: " + search_results);
   }

   String friend_list = newFriendDao.getFriendList(userEmail);

      out.write('\n');
      out.write('\n');
 if (err != null) { 
      out.write("\n");
      out.write("<font color=red><b>Error: ");
      out.print( err );
      out.write("</b></font><br><br>\n");
 } else if (addedFriend) { 
      out.write("\n");
      out.write("\n");
      out.write("<h2>Success!</h2>\n");
      out.write("\n");
      out.print( first_name + " " + last_name );
      out.write(" has been added to your friends list.<br><br>\n");
      out.write("\n");
 } 
      out.write("\n");
      out.write("\n");
      out.write("<a href=\"/photoshare/index.jsp\">Return to main page</a>\n");
      out.write("\n");
      out.write("<h2>Add Friend</h2>\n");
      out.write("<form action=\"friendList.jsp\" method=\"post\">\n");
      out.write("  Email: <input type=\"text\" name=\"email\" id=\"emailInput\"/><br>\n");
      out.write("  <input type=\"submit\" value=\"Add\"/><br>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("**or**<br><br>\n");
      out.write("\n");
      out.write("<form action=\"friendList.jsp\" method=\"post\">\n");
      out.write("  Search:<br>\n");
      out.write("  ");
      out.print( search_results );
      out.write("\n");
      out.write("  <p style=\"text-indent: 1em;\">First Name: <input type=\"text\" name=\"first\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Last Name: <input type=\"text\" name=\"last\"/></p>\n");
      out.write("  <input type=\"submit\" value=\"Search\"/><br>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("\n");
      out.write("<h2>Friend List</h2>\n");
      out.write("\n");
      out.print( friend_list );
      out.write("\n");
      out.write("\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
      }
    } finally {
      if (_jspxFactory != null) _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
