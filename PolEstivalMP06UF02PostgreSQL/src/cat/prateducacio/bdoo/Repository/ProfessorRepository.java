package cat.prateducacio.bdoo.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cat.prateducacio.bdoo.domain.Professor;

public class ProfessorRepository extends CommonRepository {
	public long insertProfessor(Professor professor) throws SQLException {
		String SQL = "INSERT INTO profesores (nombre,apellido, especialidad) " + "VALUES(?,?, ?)";

		long id = 0;

		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, professor.getNombre());
			pstmt.setString(2, professor.getApellido());
			pstmt.setString(3, professor.getEspecialidad());

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

	public int updateProfessor(Professor p) throws SQLException {
		String SQL = "UPDATE profesores " + "SET nombre = ? " + ", apellido = ? " + ", especialidad = ? "
				+ " WHERE id = ?";

		int affectedrows = 0;
		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getApellido());
			pstmt.setString(3, p.getEspecialidad());
			pstmt.setLong(4, p.getId());

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

	public int deleteProfessor(int id) throws SQLException {
		String SQL = "DELETE FROM profesores WHERE id = ?";

		int affectedrows = 0;

		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setInt(1, id);

			affectedrows = pstmt.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return affectedrows;
	}
}
