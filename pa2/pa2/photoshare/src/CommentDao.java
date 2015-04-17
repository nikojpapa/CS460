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
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */
public class CommentDao {

	private static final String GET_EMAIL_STMT = "SELECT email FROM Users WHERE uid = ?";

	private static final String GET_UID_STMT = "SELECT uid FROM Users WHERE email = ?";

	private static final String ADD_COMMENT_STMT = "INSERT INTO Comments (comment_text, comment_date, uid, pid) VALUES (?, ?, ?, ?)";

	private static final String LIST_COMMENTS_STMT = "SELECT comment_text, uid, comment_date FROM Comments WHERE pid = ? AND comment_text != 'like' ORDER BY comment_date";

	private static final String TEST_USR_STMT = "SELECT p.picture_id FROM Users u, Pictures p, Albums a WHERE u.uid = ? AND p.picture_id = ? AND u.uid = a.uid AND a.aid = p.album_id";

  	public String listComments(int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		String comments = "<table id='comments'>";
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LIST_COMMENTS_STMT);
			stmt.setInt(1, pid);
			rs = stmt.executeQuery();

			while (rs.next()) {
				stmt = conn.prepareStatement(GET_EMAIL_STMT);
				stmt.setInt(1, rs.getInt(2));
				ResultSet rs2 = stmt.executeQuery();
				if (!rs2.next()) {
					//should never happen
					return "";
				}
				String userEmail = rs2.getString(1);
				rs2.close();
				rs2 = null;

				comments += "<tr><td><b>" + userEmail + ", " + rs.getString(3) + "</b><br><p style='text-indent: 1em;'>" + rs.getString(1) + "</p></td></tr>";
			}
			comments += "</table>";

			rs.close();
			rs = null;

			stmt.close();
			stmt = null;

			conn.close();
			conn = null;
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

		return comments;
	}

  	public boolean addComment(String text, String date, String userEmail, int pid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_UID_STMT);
			stmt.setString(1, userEmail);
			rs = stmt.executeQuery();

			int uid = 0;
			if (rs.next()) {
				//user is signed in
				uid = rs.getInt(1);
			}
			System.out.println("UID: " + uid);

			if (uid == 0) {
				NewUserDao newUser = new NewUserDao();
				newUser.addAnon();
				uid = -1;
			}

			stmt = conn.prepareStatement(TEST_USR_STMT);
			stmt.setInt(1, uid);
			stmt.setInt(2, pid);
			rs = stmt.executeQuery();
			if (rs.next()) {
				//users cannot leave comments on their own pictures
				return false;
			}

			stmt = conn.prepareStatement(ADD_COMMENT_STMT);
			stmt.setString(1, text);
			stmt.setString(2, date);
			stmt.setInt(3, uid);
			stmt.setInt(4, pid);
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
}