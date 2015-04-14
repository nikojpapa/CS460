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
  private String first = "";
  private String last = "";


  public String getEmail() {
    return email;
  }

  public String getFirst() {
    return first;
  }

  public String getLast() {
    return last;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirst(String first) {
    this.first = first;
  }
  
  public void setLast(String last) {
    this.last = last;
  }
}
