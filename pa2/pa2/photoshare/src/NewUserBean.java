package photoshare;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * A bean that handles new user data
 *
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */
public class NewUserBean {
  private String first = "";
  private String last = "";
  private String email = "";
  private String dob = "";
  private String password1 = "";
  private String password2 = "";
  private String gender = "";
  private String h_city = "";
  private String h_state = "";
  private String h_country = "";
  private String c_city = "";
  private String c_state = "";
  private String c_country = "";
  private String h_name = "";
  private String h_grad = "";
  private String c_name = "";
  private String c_grad = "";

  public String saySomething() {
    System.out.println("Hello!");
    return "Test";
  }

  public String getFirst() {
    return first;
  }

  public String getLast() {
    return last;
  }

  public String getEmail() {
    return email;
  }

  public String getDOB() {
    return dob;
  }

  public String getPassword1() {
    return password1;
  }

  public String getPassword2() {
    return password2;
  }

  public String getGender() {
    return gender;
  }

  public String getHcity() {
    return h_city;
  }

  public String getHstate() {
    return h_state;
  }

  public String getHcountry() {
    return h_country;
  }

  public String getCcity() {
    return c_city;
  }

  public String getCstate() {
    return c_state;
  }

  public String getCcountry() {
    return c_country;
  }

  public String getHname() {
    return h_name;
  }

  public String getHgrad() {
    return h_grad;
  }

  public String getCname() {
    return c_name;
  }
  
  public String getCgrad() {
    return c_grad;
  }

  public void setFirst(String first) {
    this.first = first;
  }

  public void setLast(String last) {
    this.last = last;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDOB(String dob) {
    this.dob = dob;
  }

  public void setPassword1(String password1) {
    this.password1 = password1;
  }

  public void setPassword2(String password2) {
    this.password2 = password2;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setHcity(String h_city) {
    this.h_city = h_city;
  }

  public void setHstate(String h_state) {
    this.h_state = h_state;
  }

  public void setHcountry(String h_country) {
    this.h_country = h_country;
  }

  public void setCcity(String c_city) {
    this.c_city = c_city;
  }

  public void setCstate(String c_state) {
    this.c_state = c_state;
  }

  public void setCcountry(String c_country) {
    this.c_country = c_country;
  }

  public void setHname(String h_name) {
    this.h_name = h_name;
  }

  public void setHgrad(String h_grad) {
    this.h_grad = h_grad;
  }

  public void setCname(String c_name) {
    this.c_name = c_name;
  }

  public void setCgrad(String c_grad) {
    this.c_grad = c_grad;
  }
}
