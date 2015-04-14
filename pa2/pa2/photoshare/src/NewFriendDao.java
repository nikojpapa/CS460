package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle friends
 *
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */
public class NewFriendDao {

  private static final String GET_UID_STMT = "SELECT " +
      "uid FROM Users WHERE email = ?";

  private static final String CHECK_FRIEND_STMT = "SELECT " +
      "COUNT(*) FROM Friends F, (" + GET_UID_STMT + ") AS fr, (" + GET_UID_STMT + ") AS u WHERE F.fid = fr.uid AND F.uid = u.uid";

  private static final String GET_FRIENDS_STMT = "SELECT " +
      "first_name, last_name FROM Friends, Users WHERE Friends.uid IN (" + GET_UID_STMT + ") AND Friends.fid = Users.uid";

  private static final String NEW_FRIEND_STMT = "INSERT INTO " +
      "Friends (fid, uid) VALUES (?, ?)";

  public String simpleSearch(String first, String last) {
    String search_conditions = "";
    ArrayList<String> fields = new ArrayList<String>();
    if (!first.equals("")) {
      search_conditions += "first_name = ? AND ";
      fields.add(first);
    }
    if (!last.equals("")) {
      search_conditions += "last_name = ? AND ";
      fields.add(last);
    }
    int num_params = fields.size();

    String simple_search_stmt = "SELECT " +
      "first_name, last_name, email FROM Users WHERE " + search_conditions + "1=1";
    System.out.println(simple_search_stmt);

    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(simple_search_stmt);
      for (int i = 0; i < num_params; i++) {
        stmt.setString(i+1, fields.get(i));
      }
      rs = stmt.executeQuery();

      int columns = 3;
      String list = "";

      while (rs.next()) {

          String persons_email = rs.getString(columns);
          list += "<a href='javascript:void(0);' onclick=\"document.getElementById('emailInput').value = '" + persons_email + "';\">-";
          for (int i = 1; i <= columns; i++) {
            list += " " + rs.getString(i);
            if (i == 2) {
              list += " |";
            }
          }
          list += "</a><br>";
      }
      return list;

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

  public String getName(String email, String column) {
    String get_name_stmt = "SELECT " + column + " FROM Users WHERE email = ?";

    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(get_name_stmt);
      stmt.setString(1, email);
      rs = stmt.executeQuery();
      if (rs.next()) {

        int columns = rs.getMetaData().getColumnCount();
        if ( columns == 1) {
          return rs.getString(1);
        } else {
          //should never get here
          return "Name not found or more than one name";
        }
      } else {
        //should never get here
        return "No name matches this user";
      }

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

  public String getFriendList(String userEmail) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_FRIENDS_STMT);
      stmt.setString(1, userEmail);
      rs = stmt.executeQuery();

      int columns = rs.getMetaData().getColumnCount();

      String list = "";

      while (rs.next()) {

          list += "-";
          for (int i = 1; i <= columns; i++) {
            list += " " + rs.getString(i);
          }
          list += "<br>";
      }
      return list;

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

  public int getUID(String userEmail) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(GET_UID_STMT);
      stmt.setString(1, userEmail);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // if user does not exist in databse
        return -1;
      }
      return rs.getInt(1);

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

  public boolean create(String friendEmail, String userEmail) {
    PreparedStatement stmt = null;
    Connection conn = null;
    ResultSet rs = null;
    try {
      conn = DbConnection.getConnection();
      stmt = conn.prepareStatement(CHECK_FRIEND_STMT);
      stmt.setString(1, friendEmail);
      stmt.setString(2, userEmail);
      rs = stmt.executeQuery();
      if (!rs.next()) {
        // Theoretically this can't happen, but just in case...
        return false;
      }
      int result = rs.getInt(1);
      if (result > 0) {
        // This friend has already been added
        return false; 
      }
      
      try { stmt.close(); }
      catch (Exception e) { }

      int userUID = getUID(userEmail);

      int friendUID = getUID(friendEmail);

      if (userUID < 0 || friendUID < 0) {
        //either user or friend was not found
        return false;
      }

      stmt = conn.prepareStatement(NEW_FRIEND_STMT);
      stmt.setInt(1, friendUID);
      stmt.setInt(2, userUID);
      
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
