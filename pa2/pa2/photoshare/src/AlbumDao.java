package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A data access object (DAO) to handle picture objects
 *
 * @author G. Zervas <cs460tf@bu.edu>
 */
public class AlbumDao {
	private static final String GET_UID_STMT = "SELECT uid FROM Users WHERE email = ?";

 	private static final String GET_AID_STMT = "SELECT aid FROM Albums a, (" + GET_UID_STMT + ") u WHERE a.album_name = ? AND u.uid = a.uid";

    private static final String ADD_ALBUM_STMT = "INSERT INTO Albums (album_name, uid) VALUES (?, ?)";

    private static final String LIST_ALBUMS_STMT = "SELECT album_name FROM Albums a, (" + GET_UID_STMT + ") u WHERE u.uid = a.uid";

    public String listAlbums(String userEmail) {
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LIST_ALBUMS_STMT);
			stmt.setString(1, userEmail);
			rs = stmt.executeQuery();

			String name_list = "";
			
			while (rs.next()) {
				String name = rs.getString(1);
				name_list += "<p><a href='/photoshare/albums.jsp?album_name=" + name + "'>" + name + "</a></p>";
			}

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return name_list;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
    }

    public boolean addAlbum(String name, String userEmail) {
    	if (name.equals("")) {
    		//must specify album
    		return false;
    	}

    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_UID_STMT);
			stmt.setString(1, userEmail);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
				//should never happen
				return false;
			}

			int uid = rs.getInt(1);

			stmt = conn.prepareStatement(ADD_ALBUM_STMT);
			stmt.setString(1, name);
			stmt.setInt(2, uid);
			stmt.executeUpdate();

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return true;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
    }

 	public int getAID(String userEmail, String name) {
 		System.out.println(userEmail + " || " + name);
 		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int id = -1;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_AID_STMT);
			stmt.setString(1, userEmail);
			stmt.setString(2, name);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
				boolean success = addAlbum(name, userEmail);
				if (!success) {
					//album could not be added
					return -1;
				} else {
					//get the new album id
					rs = stmt.executeQuery();
					if (!rs.next()) {
						//if no album name entered
						return -1;
					}
				}
			}

			id = rs.getInt(1);

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return id;
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException(e);
		} finally {
			if (rs != null) {
				try { rs.close(); } catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); } catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); } catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
}
