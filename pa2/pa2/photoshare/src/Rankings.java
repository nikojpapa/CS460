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

	private static final String GET_COMMS_COUNT = "CREATE VIEW comms AS (SELECT u.uid AS c_uid, u.first_name AS c_first_name, u.last_name AS c_last_name, COUNT(c.cid) AS c_count FROM users u, comments c WHERE u.uid = c.uid GROUP BY u.uid)";

	private static final String DROP_COMMS_COUNT = "DROP VIEW comms";

	private static final String GET_PICS_COUNT = "CREATE VIEW pics AS (SELECT u.uid AS p_uid, u.first_name AS p_first_name, u.last_name AS p_last_name, COUNT(p.picture_id) AS p_count FROM users u, albums a, pictures p WHERE u.uid = a.uid AND a.aid = p.album_id GROUP BY u.uid)";

	private static final String DROP_PICS_COUNT = "DROP VIEW pics";

	private static final String GET_RANKINGS = "SELECT p_first_name, p_last_name, coalesce(p_count+c_count, p_count, c_count, 0) AS contribution FROM pics FULL OUTER JOIN comms ON p_uid = c_uid ORDER BY contribution DESC";

	public String getRankings() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		String results = "";

	    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DROP_COMMS_COUNT);
			stmt.execute();

			stmt = conn.prepareStatement(DROP_PICS_COUNT);
			stmt.execute();
			
			stmt = conn.prepareStatement(GET_COMMS_COUNT);
			stmt.execute();

			stmt = conn.prepareStatement(GET_PICS_COUNT);
			stmt.execute();

			stmt = conn.prepareStatement(GET_RANKINGS);
			rs = stmt.executeQuery();

			for (int i = 1; i <= 10; i++) {
				if (rs.next()) {
					String contrib = rs.getString(3);
					if (contrib == null) {
						contrib = "0";
					}
					results += i + ") " + rs.getString(1) + " " + rs.getString(2) + " | " + contrib + "<br>";
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
