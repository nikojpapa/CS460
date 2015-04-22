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
public class TagDao {
    private static final String ADD_TAG_STMT = "INSERT INTO Tags (tag_name, pid) VALUES (?, ?)";

    private static final String DELETE_TAG_STMT = "DELETE FROM Tags WHERE tid = ?";

	private static final String GET_UID_STMT = "SELECT uid FROM Users WHERE email = ?";

	private static final String GET_TID_STMT = "SELECT t.tid FROM Tags t, Users u, Albums a, Pictures p WHERE u.email = ? AND u.uid = a.uid AND a.aid = p.album_id AND p.picture_id = t.pid AND t.tag_name = ?";

    private static final String LIST_TAGS_STMT = "SELECT DISTINCT tag_name FROM Tags t, (" + GET_UID_STMT + ") u, Albums a, Pictures p WHERE u.uid = a.uid AND a.aid = p.album_id AND p.picture_id = t.pid ORDER BY tag_name";

    private static final String MOST_POPULAR_STMT = "SELECT DISTINCT tag_name, count(p.picture_id) AS count FROM Tags t, Pictures p WHERE p.picture_id = t.pid GROUP BY tag_name ORDER BY count DESC";

    public String mostPopular() {
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(MOST_POPULAR_STMT);
			rs = stmt.executeQuery();

			String list = "";
			
			while (rs.next()) {
				String name = rs.getString(1);
				list += "<p><a href='/photoshare/tags.jsp?tag_name=" + name + "&deleted=false&all=true'>" + name + "</a></p>";
			}

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return list;
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

    public boolean deleteTag(int tid) {
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_TAG_STMT);
			stmt.setInt(1, tid);
			stmt.executeUpdate();
			
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

    public String listTags(String userEmail) {
    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LIST_TAGS_STMT);
			stmt.setString(1, userEmail);
			rs = stmt.executeQuery();

			String name_list = "";
			
			while (rs.next()) {
				String name = rs.getString(1);
				name_list += "<p><a href='/photoshare/tags.jsp?tag_name=" + name + "&deleted=false'>" + name + "</a></p>";
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

    public boolean addTag(String tag_name, int pid) {
    	if (tag_name.equals("")) {
    		//must specify tag
    		return false;
    	}

    	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_TAG_STMT);
			stmt.setString(1, tag_name);
			stmt.setInt(2, pid);
			stmt.executeUpdate();
			
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

 	public int getTID(String userEmail, String tag_name) {
 		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		int id = -1;

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_TID_STMT);
			stmt.setString(1, userEmail);
			stmt.setString(2, tag_name);
			rs = stmt.executeQuery();
			
			if (!rs.next()) {
				//tag does not exist
				return -1;
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
