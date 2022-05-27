package Login;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;

public class Login extends JFrame{

	Login lp;
	Container c;

	public static void main(String[] args) {
		Login lp = new Login();
	}
	
	public Login() {

		LoginPanel lp = new LoginPanel();
		signupPanel sp = new signupPanel();
		
		this.setVisible(true);
		this.setSize(1920, 1080);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = getContentPane();
		this.setTitle("POS_Login");
		viewScreen(new LoginPanel());
	}

	void viewScreen(JPanel p) {
		c.removeAll(); //패널들을 불러오기전에 현재 존재하는 패널을 모두 안보이게함.
		c.add(p,BorderLayout.CENTER);
		c.revalidate(); //패널들을 재배치할때 사용함.
	}

	Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos_DB?serverTimezone=UTC", "root",
				"root");
		return conn;
	}

}