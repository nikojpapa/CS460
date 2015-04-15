package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A script to retrieve contribution to website
 *
 * @author Nicholas Papadopoulos <npapa@bu.edu>
 */
public class Rankings {
	private static final String GET_RANKINGS = "SELECT " +
    	"u.first_name, u.last_name, COUNT(p.picture_id) + COUNT(c.cid) AS contribution FROM Users u, Albums a, Pictures p, Comments c WHERE u.uid = a.aid AND a.aid = p.album_id AND u.uid = c.uid GROUP BY u.uid ORDER BY contribution DESC";

	private static final String GET_UID_STMT = "SELECT " +
    	"uid FROM Users WHERE email = ?";

	public String getRankings() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String results = "";

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_RANKINGS);
			rs = stmt.executeQuery();
			for (int i = 1; i <= 10; i++) {
				if (rs.next()) {
					results += i + ") " + rs.getString(1) + " " + rs.getString(2) + " | " + rs.getString(3);
				}
			}

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

		return results;
	}
}
