package cat.prateducacio.bdoo.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cat.prateducacio.bdoo.domain.Persona;

public class PersonaRepository extends CommonRepository {
	public long insertPersona(Persona persona) throws SQLException {
		String SQL = "INSERT INTO personas(nombre,apellido) " + "VALUES(?,?)";

		long id = 0;

		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, persona.getNombre());
			pstmt.setString(2, persona.getApellido());

			int affectedRows = pstmt.executeUpdate();
			// check the affected rows
			if (affectedRows > 0) {
				// get the ID back
				try (ResultSet rs = pstmt.getGeneratedKeys()) {
					if (rs.next()) {
						id = rs.getLong(1);
					}
				} catch (SQLException ex) {
					System.out.println(ex.getMessage());
				}
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return id;
	}

	public int updatePersona(Persona p) throws SQLException {
		String SQL = "UPDATE personas " + "SET nombre = ? " + ", apellido = ? " + "WHERE id = ?";

		int affectedrows = 0;
		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getApellido());
			pstmt.setLong(3, p.getId());

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return affectedrows;
	}

	public int deletePersona(long id) throws SQLException {
		String SQL = "DELETE FROM personas WHERE id = ?";

		int affectedrows = 0;

		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setLong(1, id);

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}
}
