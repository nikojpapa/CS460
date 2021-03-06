package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import photoshare.Picture;
import photoshare.PictureDao;
import photoshare.AlbumDao;
import photoshare.TagDao;
import photoshare.Rankings;
import photoshare.Recommendations;
import org.apache.commons.fileupload.FileUploadException;
import java.util.List;
import java.util.ArrayList;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      photoshare.ImageUploadBean imageUploadBean = null;
      synchronized (_jspx_page_context) {
        imageUploadBean = (photoshare.ImageUploadBean) _jspx_page_context.getAttribute("imageUploadBean", PageContext.PAGE_SCOPE);
        if (imageUploadBean == null){
          imageUploadBean = new photoshare.ImageUploadBean();
          _jspx_page_context.setAttribute("imageUploadBean", imageUploadBean, PageContext.PAGE_SCOPE);
          out.write("\n");
          out.write("    ");
          org.apache.jasper.runtime.JspRuntimeLibrary.introspect(_jspx_page_context.findAttribute("imageUploadBean"), request);
          out.write('\n');
        }
      }
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("<head><title>Photo Sharing</title></head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("<h1>A photo sharing application for CS460/660 PA2</h1>\n");
      out.write("\n");
  
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
    if (current_tags == null) {
        current_tags = "";
    }
    List<String> tagList = new ArrayList<String>();
    String suggest_tags = "";
    if (current_tags != null && !current_tags.equals("")) {
        tagList = rec.recTags(current_tags);

        for (String tagName : tagList) {
            suggest_tags += tagName + ", ";
        }
        suggest_tags = ", " + suggest_tags.substring(0, suggest_tags.length()-2);
    }

      out.write("\n");
      out.write("\n");
      out.write("Hello <b><code>");
      out.print( userEmail  );
      out.write("</code></b>, click here to\n");
      out.write("<a href=\"/photoshare/logout.jsp\">log out</a><br><br>\n");
      out.write("Click here to <a href=\"/photoshare/friendList.jsp\"> show friends list</a>\n");
      out.write("\n");
      out.write("<h2>Rankings</h2>\n");
      out.write("<p id=\"rankings\"></p>\n");
      out.write("\n");
      out.write("<h2>Upload a new picture</h2>\n");
      out.write("\n");
      out.write("<form action=\"index.jsp\" enctype=\"multipart/form-data\" method=\"post\">\n");
      out.write("    <p>Filename: <input type=\"file\" name=\"filename\"/></p>\n");
      out.write("    <p>Album: <input type=\"text\" name=\"album_name\"/></p>\n");
      out.write("    <p>Tags: <input id=\"tags\" type=\"text\" name=\"tags\"/> <a id=\"tagLink\" href=\"#\" onclick=\"\n");
      out.write("        var new_tags = document.getElementById('tags').value;\n");
      out.write("        document.getElementById('tagLink').setAttribute('href','");
      out.print(url );
      out.write("rectags=' + new_tags);\n");
      out.write("        \">Recommend Tags</a></p>\n");
      out.write("    <p>Caption: <input type=\"text\" name=\"caption\"/></p>\n");
      out.write("\n");
      out.write("    <input type=\"submit\" value=\"Upload\"/><br/>\n");
      out.write("</form>\n");
      out.print("<script>document.getElementById('tags').value = '" + current_tags + suggest_tags + "'</script>" );
      out.write('\n');
      out.write('\n');

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

    if (!err.equals("")) { 
      out.write("\n");
      out.write("        <p><font color=red><b>Error: ");
      out.print( err );
      out.write("</b></font></p>\n");
      out.write("    ");
 }

      out.write('\n');

    Rankings rankings = new Rankings();
    String ranks = rankings.getRankings();

      out.write('\n');
      out.print("<script>document.getElementById('rankings').innerHTML = '" + ranks + "'</script>" );
      out.write("\n");
      out.write("\n");
      out.write("<table>\n");
      out.write("    <th>My Albums</th><th></th><th>My Tags</th><th></th><th>Most Popular Tags</th>\n");
      out.write("    <tr>\n");
      out.write("        <td>\n");
      out.write("            ");

                AlbumDao albums = new AlbumDao();
                String users_albums = albums.listAlbums(userEmail);
            
      out.write("\n");
      out.write("            ");
      out.print( users_albums );
      out.write("\n");
      out.write("        </td><td/><td>\n");
      out.write("            ");

                TagDao tags = new TagDao();
                String users_tags = tags.listTags(userEmail);
            
      out.write("\n");
      out.write("            ");
      out.print( users_tags );
      out.write("\n");
      out.write("        </td><td/><td>\n");
      out.write("            ");
      out.print( tags.mostPopular() );
      out.write("\n");
      out.write("        </td>\n");
      out.write("    </tr>\n");
      out.write("</table>\n");
      out.write("\n");
      out.write("<h2>Existing pictures</h2>\n");
      out.write("<table>\n");
      out.write("    <tr>\n");
      out.write("        ");

            List<Integer> pictureIds = pictureDao.allPicturesIds();
            for (Integer pictureId : pictureIds) {
        
      out.write("\n");
      out.write("        <td><a href=\"/photoshare/picture.jsp?pid=");
      out.print( pictureId );
      out.write("&user=");
      out.print( userEmail );
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
      out.write("<h2>You-may-also-like</h2>\n");
      out.write("<table>\n");
      out.write("    <tr>\n");
      out.write("        ");

            pictureIds = rec.recPics(userEmail);
            for (Integer pictureId : pictureIds) {
        
      out.write("\n");
      out.write("        <td><a href=\"/photoshare/picture.jsp?pid=");
      out.print( pictureId );
      out.write("&user=");
      out.print( userEmail );
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
