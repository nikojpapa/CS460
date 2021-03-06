package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.PictureDao;
import photoshare.CommentDao;
import java.util.Date;

public final class picture_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");

int pid = Integer.parseInt(request.getParameter("pid"));
String userEmail = request.getParameter("user");
String new_comm = request.getParameter("comment_text");
PictureDao picture = new PictureDao();
CommentDao comment = new CommentDao();

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("  <head>\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, minimum-scale=0.1\">\n");
      out.write("    <title>Picture </title>\n");
      out.write("    <style type=\"text/css\"></style>\n");
      out.write("  </head>\n");
      out.write("\n");
      out.write("  <body>\n");
      out.write("    <p><a href=\"/photoshare/index.jsp\">Return to main page</a></p>\n");
      out.write("    <img style=\"-webkit-user-select: none\" src=\"http://localhost:9545/photoshare/img?picture_id=");
      out.print(pid );
      out.write("\">\n");
      out.write("    ");
      out.print( picture.getCaption(pid) );
      out.write("\n");
      out.write("    <br>\n");
      out.write("    ");
      out.print( picture.listTags(pid) );
      out.write("\n");
      out.write("\n");
      out.write("    <h2>Comments</h2>\n");
      out.write("\n");
      out.write("    <form id=\"like_pic\" action=\"picture.jsp\" method=\"post\">\n");
      out.write("      <input type=\"hidden\" name=\"pid\" value=");
      out.print(pid );
      out.write(" ></input>\n");
      out.write("      <input type=\"hidden\" name=\"user\" value=");
      out.print(userEmail );
      out.write(" ></input>\n");
      out.write("      <input type=\"hidden\" name=\"comment_text\" value = \"like\"/>\n");
      out.write("      <input type=\"submit\" value=\"Like This Picture\"> </input>\n");
      out.write("    </form>\n");
      out.write("\n");
      out.write("    <a id=\"num_likes\" href='javascript:void(0);' onclick=\"\n");
      out.write("      var div = document.getElementById('likes');\n");
      out.write("      if (div.style.display !== 'none') {\n");
      out.write("          div.style.display = 'none';\n");
      out.write("      }\n");
      out.write("      else {\n");
      out.write("          div.style.display = 'block';\n");
      out.write("      }\"></a> Likes\n");
      out.write("    <table id='likes'></table>\n");
      out.write("\n");
      out.write("    <form id=\"leave_comment\" action=\"picture.jsp\" method=\"post\">\n");
      out.write("      <input type=\"hidden\" name=\"pid\" value=");
      out.print(pid );
      out.write(" ></input>\n");
      out.write("      <input type=\"hidden\" name=\"user\" value=");
      out.print(userEmail );
      out.write(" ></input>\n");
      out.write("      <p>Leave Comment</p>\n");
      out.write("      <p><input type=\"text\" name=\"comment_text\"/></p>\n");
      out.write("      <input type=\"submit\" value=\"Submit\"> </input>\n");
      out.write("    </form>\n");
      out.write("\n");
      out.write("    <br><br>\n");
      out.write("    ");

    if (new_comm != null) {
      comment.addComment(new_comm, new Date().toLocaleString(), userEmail, pid);
    }

    String comments = comment.listComments(pid);

    String likes_list = comment.listLikes(pid);
    int num_likes = 0;
    if (!likes_list.equals("")) {
      num_likes = Integer.parseInt(likes_list.substring(likes_list.length() - 1));

      likes_list = likes_list.substring(0, likes_list.length()-1);
    };
    
      out.write("\n");
      out.write("    ");
      out.print("<script>document.getElementById('num_likes').text = '" + num_likes + "'; document.getElementById('likes').innerHTML = '" + likes_list.replace("'", "\\'") + "'; document.getElementById('likes').style.display = 'none';</script>"
    );
      out.write("\n");
      out.write("\n");
      out.write("    ");
      out.print( comments );
      out.write("\n");
      out.write("\n");
      out.write("  </body>\n");
      out.write("</html>");
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
