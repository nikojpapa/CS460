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
public class Recommendations {

	private static final String MOST_COMMON_TAGS_STMT = "SELECT t.tag_name, COUNT(t.tid) FROM Tags t, Users u, Albums a, Pictures p WHERE u.email = ? AND u.uid = a.uid AND a.aid = p.album_id AND p.picture_id = t.pid GROUP BY t.tag_name ORDER BY COUNT(t.tid) DESC LIMIT 5";

	public List<String> recTags(String currentTags) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		List<String> tags = new ArrayList<String>();

	    try {
			conn = DbConnection.getConnection();
			String[] tag_array = currentTags.split("( |,|, )");

			String rec_tags = "SELECT t.tag_name, COUNT(t.tid) AS count FROM Tags t, (SELECT DISTINCT p.picture_id AS pid FROM Tags t2, Pictures p WHERE (";

			for (String tagName : tag_array) {
				rec_tags += "t2.tag_name = '" + tagName + "' OR ";
			}
			rec_tags += "1=2) AND p.picture_id = t2.pid) pics WHERE pics.pid = t.pid AND";
			
			for(String tagName : tag_array) {
				rec_tags += " t.tag_name != '" + tagName + "' AND ";
			}
			rec_tags += "1=1 GROUP BY t.tag_name ORDER BY count DESC LIMIT 5";

			System.out.println("QUERY: " + rec_tags);
			stmt = conn.prepareStatement(rec_tags);
			rs = stmt.executeQuery();

			while (rs.next()) {
				tags.add(rs.getString(1));
			}

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return tags;
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

	public List<Integer> recPics(String userEmail) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

		List<Integer> picturesIds = new ArrayList<Integer>();

	    try {
			conn = DbConnection.getConnection();

			List<String> tag_names = new ArrayList<String>();
			
			stmt = conn.prepareStatement(MOST_COMMON_TAGS_STMT);
			stmt.setString(1, userEmail);
			rs = stmt.executeQuery();

			while (rs.next()) {
				tag_names.add(rs.getString(1));
			}

			String rec_pics = "SELECT t.pid, COUNT(t.tag_name) AS matching, SUM(ft.count) AS concision FROM Tags t, (SELECT t2.pid, COUNT(t2.tag_name) AS count FROM Tags t2 GROUP BY t2.pid ORDER BY count) ft WHERE (";

			for (String tagName : tag_names) {
				rec_pics += "t.tag_name = '" + tagName + "' OR ";
			}
			rec_pics += "1=2) AND t.pid = ft.pid GROUP BY t.pid ORDER BY matching DESC, concision LIMIT 5";

			stmt = conn.prepareStatement(rec_pics);
			rs = stmt.executeQuery();

			while (rs.next()) {
				picturesIds.add(rs.getInt(1));
			}

			rs.close();
			rs = null;
			
			stmt.close();
			stmt = null;
				
			conn.close();
			conn = null;

			return picturesIds;
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
