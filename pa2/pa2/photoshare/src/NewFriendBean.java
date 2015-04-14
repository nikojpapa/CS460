package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new friend data
 *
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */
public class NewFriendBean {
  private String email = "";

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
  // public int getUID() {
  //   return uid;
  // }

  // public void setFID(int fid) {
  //   this.fid = fid;
  // }

  // public void setUID(String uid) {
  //   this.uid = uid;
  // }
}
