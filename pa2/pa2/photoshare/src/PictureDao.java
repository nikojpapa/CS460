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
public class PictureDao {
  private static final String LOAD_PICTURE_STMT = "SELECT " +
      "\"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\" FROM Pictures WHERE \"picture_id\" = ?";

  private static final String DELETE_PICTURE_STMT = "DELETE FROM Pictures WHERE picture_id = ?";

  private static final String SAVE_PICTURE_STMT = "INSERT INTO " +
      "Pictures (\"album_id\", \"caption\", \"imgdata\", \"thumbdata\", \"size\", \"content_type\") VALUES (?, ?, ?, ?, ?, ?)";

  private static final String ALL_PICTURE_IDS_STMT = "SELECT \"picture_id\" FROM Pictures ORDER BY \"picture_id\" DESC";

  private static final String ALBUM_PICTURE_IDS_STMT = "SELECT picture_id FROM Pictures WHERE album_id = ? ORDER BY picture_id DESC";

  private static final String TAG_PICTURE_IDS_STMT = "SELECT picture_id FROM Users u, Albums a, Pictures p, Tags t WHERE t.tag_name = ? AND p.picture_id = t.pid AND u.email = ? AND u.uid = a.uid AND a.aid = p.album_id ORDER BY picture_id DESC";

  private static final String TAG_ALL_PICTURE_IDS_STMT = "SELECT picture_id FROM Pictures p, Tags t WHERE t.tag_name = ? AND p.picture_id = t.pid ORDER BY picture_id DESC";


  private static final String PIC_TAGS_STMT = "SELECT tag_name FROM Tags t WHERE t.pid = ? ORDER BY tag_name DESC";

  private static final String GET_UID_STMT = "SELECT uid FROM Users u, Albums a, Pictures p WHERE p.picture_id = ? AND p.album_id = a.aid AND a.uid = u.uid";


  public String listTags(int pid) {
  	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(PIC_TAGS_STMT);
			stmt.setInt(1, pid);
			rs = stmt.executeQuery();

			String list = "";
			while (rs.next()) {
				list += rs.getString(1) + ", ";
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

  public boolean delete(int pid) {
	  	PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_PICTURE_STMT);
			stmt.setInt(1, pid);
			stmt.executeUpdate();

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

		return true;
  }

  public Picture load(int id) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
    try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);
      stmt.setInt(1, id);
			rs = stmt.executeQuery();
      if (rs.next()) {
        picture = new Picture();
        picture.setId(id);
        picture.setCaption(rs.getString(1));
        picture.setData(rs.getBytes(2));
        picture.setThumbdata(rs.getBytes(3));
        picture.setSize(rs.getLong(4));
        picture.setContentType(rs.getString(5));
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

		return picture;
	}

	public void save(Picture picture) {
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);
			stmt.setInt(1, picture.getAid());
			stmt.setString(2, picture.getCaption());
			stmt.setBytes(3, picture.getData());
			stmt.setBytes(4, picture.getThumbdata());
			stmt.setLong(5, picture.getSize());
			stmt.setString(6, picture.getContentType());
			stmt.executeUpdate();
			
			stmt.close();
			stmt = null;
			
			conn.close();
			conn = null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
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

	public List<Integer> tagPictureIds(String tag_name,  String userEmail) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			String stmtStr = "";
			if (userEmail.equals("all")) {
				stmtStr = TAG_ALL_PICTURE_IDS_STMT;
			} else {
				stmtStr = TAG_PICTURE_IDS_STMT;
			}
			System.out.println(stmtStr);
			stmt = conn.prepareStatement(stmtStr);
			stmt.setString(1, tag_name);
			if (!userEmail.equals("all")) {
				stmt.setString(2, userEmail);
			}
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

		return picturesIds;
	}

	public List<Integer> albumPictureIds(int aid) {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALBUM_PICTURE_IDS_STMT);
			stmt.setInt(1, aid);
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

		return picturesIds;
	}

	public List<Integer> allPicturesIds() {
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		List<Integer> picturesIds = new ArrayList<Integer>();
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURE_IDS_STMT);
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

		return picturesIds;
	}
}
