package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle the Users table
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class NewUserDao {
  private static final String CHECK_EMAIL_STMT = "SELECT " +
      "COUNT(*) FROM Users WHERE email = ?";

  private static final String NEW_USER_STMT = "INSERT INTO " +
      "Users (first_name, last_name, email, dob, password, gender, hometown_city, hometown_state, hometown_country, current_city, current_state, current_country, highschool, highschool_grad, college, college_grad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

  private static final String INSERT_ANONYMOUS_STMT = "INSERT INTO Users (uid, first_name, last_name, email, dob, password) VALUES (?, ?, ?, ?, ?, ?);";

  public boolean addAnon() {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
      stmt.setString(1, "anonymous");
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This email is already in use
        return false; 
      }

      System.out.println("hi");
      stmt = conn.prepareStatement(INSERT_ANONYMOUS_STMT);
      stmt.setInt(1, -1);
      stmt.setString(2, "anonymous");
      stmt.setString(3, "");
      stmt.setString(4, "anonymous");
      stmt.setString(5, "");
      stmt.setString(6, "");
      stmt.executeUpdate();
      return true;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    }
  }

  public boolean create(String first, String last, String email, String dob, String password, String gender, String h_city, String h_state, String h_country, String c_city, String c_state, String c_country, String h_name, String h_grad, String c_name, String c_grad) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_EMAIL_STMT);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This email is already in use
        return false; 
      }
      
      try { stmt.close(); }
      catch (Exception e) { }

      stmt = conn.prepareStatement(NEW_USER_STMT);
      stmt.setString(1, first);
      stmt.setString(2, last);
      stmt.setString(3, email);
      stmt.setString(4, dob);
      stmt.setString(5, password);
      stmt.setString(6, gender);
      stmt.setString(7, h_city);
      stmt.setString(8, h_state);
      stmt.setString(9, h_country);
      stmt.setString(10, c_city);
      stmt.setString(11, c_state);
      stmt.setString(12, c_country);
      stmt.setString(13, h_name);
      stmt.setString(14, h_grad);
      stmt.setString(15, c_name);
      stmt.setString(16, c_grad);
      
      stmt.executeUpdate();

      return true;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      if (rs != null) {
        try { rs.close(); }
        catch (SQLException e) { ; }
        rs = null;
      }
      
      if (stmt != null) {
        try { stmt.close(); }
        catch (SQLException e) { ; }
        stmt = null;
      }
      
      if (conn != null) {
        try { conn.close(); }
        catch (SQLException e) { ; }
        conn = null;
      }
    }
  }
}
