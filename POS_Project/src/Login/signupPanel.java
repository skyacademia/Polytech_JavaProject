package Login;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

public class signupPanel extends JPanel {

	JTextField idTf;
	JPasswordField passTf;
	JPasswordField passReTf;
	JTextField nameTf;
	JTextField phoneTf;
	JButton registerButton;
	Font font = new Font("회원가입", Font.BOLD, 40);

	String id = "", pass = "", passRe = "", name = "", phone = "";
	Login lp;
	public signupPanel() {

		this.setSize(1920, 1080);
		this.setLayout(null);
		
		JLabel idLabel = new JLabel("아이디 : ");
		idLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		idLabel.setBounds(686, 164, 126, 60);
		
		JLabel passLabel = new JLabel("비밀번호 : ");
		passLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		passLabel.setBounds(655, 234, 157, 60);
		
		JLabel passReLabel = new JLabel("비밀번호 확인 : ");
		passReLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		passReLabel.setBounds(553, 304, 259, 60);
		
		JLabel nameLabel = new JLabel("가게 이름 : ");
		nameLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		nameLabel.setBounds(642, 377, 170, 60);
		
		JLabel phoneLabel = new JLabel("연락처 : ");
		phoneLabel.setFont(new Font("궁서", Font.PLAIN, 30));
		phoneLabel.setBounds(609, 447, 203, 60);

		idTf = new JTextField(15);
		idTf.setBounds(824, 164, 290, 60);
		
		passTf = new JPasswordField(15);
		passTf.setBounds(824, 234, 290, 60);
		
		passReTf = new JPasswordField(15);
		passReTf.setBounds(824, 304, 290, 60);
		
		nameTf = new JTextField(15);
		nameTf.setBounds(824, 377, 290, 60);
		
		phoneTf = new JTextField(11);
		phoneTf.setBounds(824, 447, 290, 60);

		this.add(idLabel);
		this.add(idTf); // 아이디

		this.add(passLabel);
		this.add(passTf); // pass
		JLabel label = new JLabel("특수문자 + 8자");
		label.setFont(new Font("굴림", Font.PLAIN, 20));
		label.setBounds(1126, 244, 140, 40);
		this.add(label); // 보안설정

		this.add(passReLabel);
		this.add(passReTf); // password 재확인

		this.add(nameLabel);
		this.add(nameTf); // 이름

		this.add(phoneLabel);
		this.add(phoneTf);

		JLabel signupLabel = new JLabel("회원가입 화면 ");
		signupLabel.setBounds(800, 8, 270, 56);
		signupLabel.setFont(font);
		signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		registerButton = new JButton("회원가입");
		registerButton.setBounds(824, 517, 290, 60);
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(signupLabel);
		this.add(registerButton);

		registerButton.addActionListener(new ActionListener() {      //회원가입 버튼

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				id = idTf.getText();
				pass = new String(passTf.getPassword());
				passRe = new String(passReTf.getPassword());
				name = nameTf.getText();
				phone = phoneTf.getText();

				String sql = "insert into user_info(uId, uPwd, uStoreName, uPhone, grade) values (?,?,?,?,?)";

				Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"); //8자 영문+특수문자+숫자
				Matcher passMatcher = passPattern1.matcher(pass);

				if (!passMatcher.find()) {
					JOptionPane.showMessageDialog(null, "비밀번호는 영문+특수문자+숫자 8자로 구성되어야 합니다.", "비밀번호 오류", 0);
				} else if (!pass.equals(passRe)) {
					JOptionPane.showMessageDialog(null, "비밀번호가 서로 맞지 않습니다.", "비밀번호 오류", 0);

				} else {
					try {
						SqlConnection sqlConn = new SqlConnection();
						Connection conn = sqlConn.getConnection();

						PreparedStatement pstmt = conn.prepareStatement(sql);


						pstmt.setString(1, idTf.getText());
						pstmt.setString(2, pass);
						pstmt.setString(3, nameTf.getText());
						pstmt.setString(4, phoneTf.getText());
						pstmt.setString(5, "0");

						int r = pstmt.executeUpdate();
						System.out.println("변경된 row " + r);
						JOptionPane.showMessageDialog(null, "회원 가입 완료!", "회원가입", 1);
						Login lps = (Login)((JButton)e.getSource()).getTopLevelAncestor();
						lps.viewScreen(new LoginPanel());
						//패널 전환 넣어야됨!
					} catch (SQLException e1) {
						System.out.println("SQL error" + e1.getMessage());
						if (e1.getMessage().contains("PRIMARY")) {
							JOptionPane.showMessageDialog(null, "아이디 중복!", "아이디 중복 오류", 0);
						} else
							JOptionPane.showMessageDialog(null, "정보를 제대로 입력해주세요!", "오류", 0);
					} // try ,catch
				}
			}
		});

	}
}