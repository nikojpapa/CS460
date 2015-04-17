package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */

public class NewCommentBean {
  private String comment_text = "";
  private String date = "";
  private int uid = -1;
  private int pid = -1;
  private String user = "";

  public String getText() {
    return comment_text;
  }

  public String getDate() {
    return date;
  }

  public int getUID() {
    return uid;
  }

  public int getPID() {
    return pid;
  }

  public void setCommentText(String comment_text) {
    this.comment_text = comment_text;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void setUID(int uid) {
    this.uid = uid;
  }

  public void setPID(int pid) {
    this.pid = pid;
  }

  public void setUser(String user) {
    this.user = user;
  }
}
