package cat.prateducacio.bdoo.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommonRepository {

	public enum Taula {
		Persones("personas"), Estudiants("estudiantes"), Professors("profesores");

		private String nomTaula;

		private Taula(String nom) {
			this.nomTaula = nom;
		}

		public String getNomTaula() {
			return this.nomTaula;
		}

	}

	private final String url = "jdbc:postgresql://localhost/mp06";
	private final String user = "postgres";
	private final String password = "root";

	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, user, password);
		return connection;

	}

	public int getRowsCount(Taula taula) throws SQLException {
		String SQL = "SELECT count(*) FROM " + taula.getNomTaula() + ";";
		int count = 0;
		Connection conn = null;
		try {
			conn = this.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (conn != null)
				conn.close();
		}
		System.out.println(count);
		return count;
	}

	public void getRowsTaula(Taula taula) throws SQLException {
		String SQL = "SELECT * FROM " + taula.getNomTaula() + ";";
		Connection conn = null;
		try {
			conn = this.getConnection();

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			showRows(taula, rs);
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		} finally {
			if (conn != null)
				conn.close();
		}

	}

	private void showRows(Taula taula, ResultSet rs) throws SQLException {
		while (rs.next()) {
			// System.out.println(rs.toString());
			if (taula == Taula.Persones) {
				System.out
						.println(rs.getString("id") + ", " + rs.getString("nombre") + ", " + rs.getString("apellido"));

			} else if (taula == Taula.Professors) {
				System.out.println(rs.getString("id") + ", " + rs.getString("nombre") + ", " + rs.getString("apellido")
						+ ", " + rs.getString("especialidad"));
			} else if (taula == Taula.Estudiants) {
				System.out.println(rs.getString("id") + ", " + rs.getString("nombre") + ", " + rs.getString("apellido")
						+ ", " + rs.getInt("curso"));
			}

		}
	}
}
