package Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection {
	Connection c;
	SqlConnection() throws SQLException{
			this.c = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos_DB?serverTimezone=UTC", "root",
					"root");
		}
	Connection getConnection() {
		return this.c;
	}
}