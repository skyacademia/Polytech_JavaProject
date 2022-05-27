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
		c.removeAll(); //�гε��� �ҷ��������� ���� �����ϴ� �г��� ��� �Ⱥ��̰���.
		c.add(p,BorderLayout.CENTER);
		c.revalidate(); //�гε��� ���ġ�Ҷ� �����.
	}

	Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Pos_DB?serverTimezone=UTC", "root",
				"root");
		return conn;
	}

}