package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MenuInfo {
	private Connection c;
	ArrayList <Object[]> menu = new ArrayList<Object[]>();
	
	MenuInfo() throws SQLException{
		SqlConnection sqlConn= new SqlConnection();
		this.c = sqlConn.getConnection();
		
	}
	public ArrayList <Object[]> getMenuInfo() throws SQLException {
		String sql = "select * from menu_info";
		Statement st = c.createStatement();
		ResultSet rs = st.executeQuery(sql);
		int i = 0;
		while(rs.next()) {
			Object [] addingMenu = new Object[4];
			addingMenu[i] = rs.getInt("mId");
			i++;
			addingMenu[i] = rs.getString("mCategory");
			i++;
			addingMenu[i] = rs.getString("mMenu");
			i++;
			addingMenu[i] = rs.getInt("mPrice");
			i = 0;
			menu.add(addingMenu);
		}
		return menu;
	}
	public Connection getConnection() {
		return this.c;
	}
}
