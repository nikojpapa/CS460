package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.NewFriendDao;
import photoshare.AlbumDao;
import photoshare.PictureDao;
import java.util.List;

public final class albums_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");

  AlbumDao album = new AlbumDao();
  String album_name = request.getParameter("album_name");
  String userEmail = request.getUserPrincipal().getName();
  int aid = album.getAID(userEmail, album_name);

      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head><title>Album ");
      out.print(album_name );
      out.write("</title></head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<a href=\"/photoshare/index.jsp\">Return to main page</a>\n");
      out.write("<p>Click here to <a href=\"/photoshare/index.jsp\" onclick=");
 
      out.write(" >Delete Album</a></p>\n");
      out.write("\n");
      out.write("<h2>");
      out.print(album_name );
      out.write(" Pictures</h2>\n");
      out.write("<table>\n");
      out.write("    <tr>\n");
      out.write("        ");

            PictureDao pictures = new PictureDao();
            List<Integer> pictureIds = pictures.albumPictureIds(aid);
            for (Integer pictureId : pictureIds) {
        
      out.write("\n");
      out.write("        <td><a href=\"/photoshare/img?picture_id=");
      out.print( pictureId );
      out.write("\">\n");
      out.write("            <img src=\"/photoshare/img?t=1&picture_id=");
      out.print( pictureId );
      out.write("\"/>\n");
      out.write("        </a>\n");
      out.write("        </td>\n");
      out.write("        ");

            }
        
      out.write("\n");
      out.write("    </tr>\n");
      out.write("</table>\n");
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
