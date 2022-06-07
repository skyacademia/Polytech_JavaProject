package Login;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
		idLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		idLabel.setBounds(650, 364, 180, 65);
		this.add(idLabel);

		idTextField = new JTextField(15);
		idTextField.setFont(new Font("궁서", Font.PLAIN, 30));
		idTextField.setBounds(842, 364, 410, 65);
		this.add(idTextField);

		JLabel passLabel = new JLabel(" 비밀번호 : ");
		passLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		passLabel.setBounds(650, 459, 180, 65);
		this.add(passLabel);

		passTextField = new JPasswordField(15);
		passTextField.setFont(new Font("궁서", Font.PLAIN, 30));
		passTextField.setBounds(842, 459, 410, 65);
		this.add(passTextField);
		
		
		JButton loginButton = new JButton("로그인");
		loginButton.setBounds(1052, 554, 200, 80);
		this.add(loginButton);

		JButton signupButton = new JButton("회원가입");
		signupButton.setBounds(842, 554, 200, 80);
		this.add(signupButton);

		loginButton.addActionListener(this);

		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();
				sp.viewScreen(new signupPanel());
			}
		});

		passTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}
		});
		idTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String id = idTextField.getText();
		String pass = new String(passTextField.getPassword());
		Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();

		try {
			SqlConnection sqlConn = new SqlConnection();
			Connection conn = sqlConn.getConnection();
			String sql_query = String.format("SELECT uPwd,grade,uStoreName FROM user_info WHERE uId = '%s' AND uPwd ='%s'", id, pass);

			Statement stmt = conn.createStatement();

			ResultSet rset = stmt.executeQuery(sql_query);
			rset.next();

			if (pass.equals(rset.getString(1))) {
				JOptionPane.showMessageDialog(this, "Login Success", "로그인 성공", 1);
				sp.viewScreen(new Main());
				//메인 패널로 옮겨야됩니다.
			} else
				JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 0);

		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(this, "Login Failed", "로그인 실패", 0);
			System.out.println("SQLException" + ex);
		}

	}
}
