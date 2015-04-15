package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.NewUserDao;

public final class newuser_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      photoshare.NewUserBean newUserBean = null;
      synchronized (_jspx_page_context) {
        newUserBean = (photoshare.NewUserBean) _jspx_page_context.getAttribute("newUserBean", PageContext.PAGE_SCOPE);
        if (newUserBean == null){
          newUserBean = new photoshare.NewUserBean();
          _jspx_page_context.setAttribute("newUserBean", newUserBean, PageContext.PAGE_SCOPE);
        }
      }
      out.write('\n');
      org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("newUserBean"), request);
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head><title>New User</title></head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("<!-- We want to show the form unless we successfully create a new user -->\n");
 boolean showForm = true;
   String err = null; 
      out.write('\n');
      out.write('\n');
 if (!newUserBean.getEmail().equals("")) {
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

      out.write('\n');
      out.write('\n');
 if (err != null) { 
      out.write("\n");
      out.write("<font color=red><b>Error: ");
      out.print( err );
      out.write("</b></font>\n");
 } 
      out.write('\n');
      out.write('\n');
 if (showForm) { 
      out.write("\n");
      out.write("\n");
      out.write("<h2>New user info</h2>\n");
      out.write("\n");
      out.write("<form action=\"newuser.jsp\" method=\"post\">\n");
      out.write("  **Required**<br>\n");
      out.write("  <p style=\"text-indent: 1em;\">First Name: <input type=\"text\" name=\"first\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Last Name: <input type=\"text\" name=\"last\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Email: <input type=\"text\" name=\"email\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Date of Birth: <input type=\"text\" name=\"dob\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Password: <input type=\"password\" name=\"password1\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Re-enter password: <input type=\"password\" name=\"password2\"/></p>\n");
      out.write("  <input type=\"submit\" value=\"Create\"/><br/>\n");
      out.write("  <br>\n");
      out.write("  **Optional**<br>\n");
      out.write("  <p style=\"text-indent: 1em;\">Gender: <input type=\"text\" name=\"gender\"/></p>\n");
      out.write("  <br>\n");
      out.write("  <p style=\"text-indent: 1em;\">--Hometown--</p>\n");
      out.write("  <p style=\"text-indent: 1em;\">City: <input type=\"text\" name=\"h_city\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">State: <input type=\"text\" name=\"h_state\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Country: <input type=\"text\" name=\"h_country\"/></p>\n");
      out.write("  <br>\n");
      out.write("  <p style=\"text-indent: 1em;\">--Current Location--</p>\n");
      out.write("  <p style=\"text-indent: 1em;\">City: <input type=\"text\" name=\"c_city\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">State: <input type=\"text\" name=\"c_state\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Country: <input type=\"text\" name=\"c_country\"/></p>\n");
      out.write("  <br>\n");
      out.write("  <p style=\"text-indent: 1em;\">--Education--</p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Highschool Name: <input type=\"text\" name=\"h_name\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">Highschool Graduation Date: <input type=\"text\" name=\"h_grad\"/></p>\n");
      out.write("  <br>\n");
      out.write("  <p style=\"text-indent: 1em;\">College Name: <input type=\"text\" name=\"c_name\"/></p>\n");
      out.write("  <p style=\"text-indent: 1em;\">College Graduation Date: <input type=\"text\" name=\"c_grad\"/></p>\n");
      out.write("  <input type=\"submit\" value=\"Create\"/><br/>\n");
      out.write("</form>\n");
      out.write("\n");
 }
   else { 
      out.write("\n");
      out.write("\n");
      out.write("<h2>Success!</h2>\n");
      out.write("\n");
      out.write("<p>A new user has been created with email ");
      out.print( newUserBean.getEmail() );
      out.write(".\n");
      out.write("You can now return to the <a href=\"login.jsp\">login page</a>.\n");
      out.write("\n");
 } 
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
