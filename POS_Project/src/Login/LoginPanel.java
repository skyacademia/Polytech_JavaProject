package Login;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginPanel extends JPanel implements ActionListener{

	JTextField idTextField;
	JPasswordField passTextField;
	Font font = new Font("회원가입", Font.BOLD, 40);
	Login lp;
	String StoreName = "";
	
	
	public LoginPanel() {
		this.setSize(1920, 1080);
		this.setLayout(null);

		JLabel loginLabel = new JLabel("로그인 화면");
		loginLabel.setFont(font);
		this.setLayout(null);

		JLabel idLabel = new JLabel(" 아이디 : ");
		idLabel.setBounds(101, 121, 52, 15);
		this.add(idLabel);

		idTextField = new JTextField(15);
		idTextField.setBounds(165, 118, 171, 21);
		this.add(idTextField);

		JLabel passLabel = new JLabel(" 비밀번호 : ");
		passLabel.setBounds(101, 152, 64, 15);
		this.add(passLabel);

		passTextField = new JPasswordField(15);
		passTextField.setBounds(165, 149, 171, 21);
		this.add(passTextField);

		JButton loginButton = new JButton("로그인");
		loginButton.setBounds(165, 180, 81, 23);
		this.add(loginButton);

		JButton signupButton = new JButton("회원가입");
		signupButton.setBounds(251, 180, 88, 23);
		this.add(signupButton);

		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();
				sp.viewScreen(new Main());
			}
		});

		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();
				sp.viewScreen(new signupPanel());
			}
		});
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String id = idTextField.getText();
		String pass = passTextField.getText();
		Login lp = new Login();

		try {

			String sql_query = String.format("SELECT uPwd,grade,uStoreName FROM user_info WHERE uId = '%s' AND uPwd ='%s'", id, pass);

			Connection conn = lp.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery(sql_query);
			rset.next();

			if (pass.equals(rset.getString(1))) {
				JOptionPane.showMessageDialog(this, "Login Success", "로그인 성공", 1);
				Member mb = new Member(rset.getString(3), rset.getInt(2));
				System.out.println(mb);
				//메인 패널로 옮겨야됩니다.
			} else
				JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 0);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 0);
			System.out.println("SQLException" + ex);
		}

	}
	
}
