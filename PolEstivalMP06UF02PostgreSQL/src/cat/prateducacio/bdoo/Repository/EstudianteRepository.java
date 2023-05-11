package cat.prateducacio.bdoo.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cat.prateducacio.bdoo.domain.Estudiante;

public class EstudianteRepository extends CommonRepository {
	public long insertEstudiante(Estudiante estudiante) throws SQLException {
		String SQL = "INSERT INTO estudiantes (nombre,apellido, curso) " + "VALUES(?,?, ?)";

		long id = 0;

		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

			pstmt.setString(1, estudiante.getNombre());
			pstmt.setString(2, estudiante.getApellido());
			pstmt.setInt(3, estudiante.getCurso());

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

	public int updateEstudiante(Estudiante p) throws SQLException {
		String SQL = "UPDATE estudiantes " + "SET nombre = ? " + ", apellido = ? " + ", curso = ? "
				+ " WHERE id = ?";

		int affectedrows = 0;
		Connection conn = this.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, p.getNombre());
			pstmt.setString(2, p.getApellido());
			pstmt.setInt(3, p.getCurso());
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

	public int deleteEstudiante(int id) throws SQLException {
		String SQL = "DELETE FROM estudiantes WHERE id = ?";

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
