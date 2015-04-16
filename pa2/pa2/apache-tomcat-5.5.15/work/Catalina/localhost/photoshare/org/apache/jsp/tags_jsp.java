package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.TagDao;
import photoshare.PictureDao;
import java.util.List;

public final class tags_jsp extends org.apache.jasper.runtime.HttpJspBase
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

  TagDao tag = new TagDao();
  String tag_name = request.getParameter("tag_name");
  String url = "tags.jsp?tag_name=" + tag_name;
  String userEmail = request.getUserPrincipal().getName();
  int tid = tag.getTID(tag_name);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head><title>Tag ");
      out.print(tag_name );
      out.write("</title></head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<a href=\"/photoshare/index.jsp\">Return to main page</a>\n");
      out.write("<form id=\"delete_form\" action=\"tags.jsp\" method=\"post\">\n");
      out.write("  <input type=\"hidden\" name=\"tag_name\" value=");
      out.print(tag_name );
      out.write(" />\n");
      out.write("  <input type=\"hidden\" name=\"deleted\" value=\"true\"/>\n");
      out.write("  <p>Click here to <input type=\"submit\" value=\"Delete Tag\"/></p>\n");
      out.write("</form>\n");
      out.write("\n");
      out.write("<h2 id=\"page_head\">");
      out.print(tag_name );
      out.write(" Pictures</h2>\n");
      out.write("<table id=\"pics\">\n");
      out.write("    <tr>\n");
      out.write("        ");

            PictureDao pictures = new PictureDao();
            String pid = request.getParameter("delete_pic");
            if (!(pid == null)) {
              pictures.delete(Integer.parseInt(pid));
            }
            List<Integer> pictureIds = pictures.tagPictureIds(tag_name);
            for (Integer pictureId : pictureIds) {
        
      out.write("\n");
      out.write("        <td><a href=\"/photoshare/img?picture_id=");
      out.print( pictureId );
      out.write("\">\n");
      out.write("            <img src=\"/photoshare/img?t=1&picture_id=");
      out.print( pictureId );
      out.write("\"/>\n");
      out.write("        </a><br>\n");
      out.write("        <form action=\"tags.jsp\" method=\"post\">\n");
      out.write("          <input type=\"hidden\" name=\"tag_name\" value=");
      out.print(tag_name );
      out.write(" />\n");
      out.write("          <input type=\"hidden\" name=\"deleted\" value=\"false\"/>\n");
      out.write("          <input type=\"hidden\" name=\"delete_pic\" value=");
      out.print( pictureId );
      out.write(" />\n");
      out.write("          <input type=\"submit\" value=\"Delete Picture\"/>\n");
      out.write("        </form>\n");
      out.write("        </td>\n");
      out.write("        ");

            }
        
      out.write("\n");
      out.write("    </tr>\n");
      out.write("</table>\n");
      out.write("\n");
 
if (request.getParameter("deleted").equals("true")) { 
      out.write('\n');
      out.write(' ');
      out.write(' ');
 tag.deleteTag(tid); 
      out.write("\n");
      out.write("  <script>\n");
      out.write("  document.getElementById(\"delete_form\").remove();\n");
      out.write("  document.getElementById(\"pics\").remove();\n");
      out.write("  document.getElementById(\"page_head\").innerText = \"Tag Deleted\";\n");
      out.write("  </script>\n");
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
