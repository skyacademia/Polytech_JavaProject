package Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PersonalInfo extends JPanel implements ActionListener{
	
	Member m = new Member();
	JTextField[] personInfoInput = new JTextField[5];
	
	PersonalInfo(){
		
		setLayout(new BorderLayout());
		TopMenu menu = new TopMenu();
		JPanel infoPanel = new JPanel();
		
		JPanel info_sub_1 = new JPanel();
		JPanel info_sub_2 = new JPanel();
		
		info_sub_1.setLayout(new GridLayout(5,2));
		String[] personInfoText = {"아이디", "새비밀번호", "새비밀번호 확인", "연락처", "가게 이름"};
		for (int i=0; i<personInfoText.length; i++) {
			JPanel newPanel = new JPanel();
			personInfoInput[i] = new JTextField(30);
			newPanel.add(new JLabel(personInfoText[i]));
			newPanel.add(personInfoInput[i]);
			info_sub_1.add(newPanel);
		}
		personInfoInput[0].setText(m.getuId());
		personInfoInput[3].setText(m.getPhone());
		personInfoInput[4].setText(m.getStoreName());
		if (!m.getuId().equals("admin"))
			personInfoInput[0].setEnabled(false);
		JButton[] menuInfoBtn = {new JButton("정보수정"),new JButton("계정삭제")};
		for (int i=0; i<menuInfoBtn.length; i++) {
			info_sub_2.add(menuInfoBtn[i]);
			menuInfoBtn[i].addActionListener(this);
		}
		
		
		infoPanel.setLayout(new GridLayout(2,1,20,0));
		infoPanel.add(info_sub_1);
		infoPanel.add(info_sub_2);
		
		add("North",menu);
		add("Center",infoPanel);
		
		this.setVisible(true);
		this.setSize(1920, 1080);
	
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn  = (JButton)e.getSource();
		Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();
		String password;
		switch(btn.getText()){
		case "정보수정":

			if (personInfoInput[1].getText().equals(personInfoInput[2].getText())){
				String pass = JOptionPane.showInputDialog("현재 로그인한 아이디의 비밀번호를 입력해주세요.");
				if(pass.equals(m.getPassword())) {
					JOptionPane.showMessageDialog(this, "입력하신 비밀번호는 현재 로그인한 아이디와 같습니다.", "인증 성공", 1);
					if (personInfoInput[1].getText().equals("") && personInfoInput[2].getText().equals("")) {
						password = m.getPassword();
					}else {
						password = personInfoInput[1].getText();
					}
					try {
						String sql = String.format("UPDATE user_info SET uId = ?, uPwd = ?, uPhone = ?, uStoreName = ? WHERE uId = ?");

						SqlConnection sqlConn = new SqlConnection();
						Connection conn = sqlConn.getConnection();

						PreparedStatement pstmt = conn.prepareStatement(sql);


						pstmt.setString(1, personInfoInput[0].getText());
						pstmt.setString(2, password);
						pstmt.setString(3, personInfoInput[3].getText());
						pstmt.setString(4, personInfoInput[4].getText());
						pstmt.setString(5, personInfoInput[0].getText());

						int r = pstmt.executeUpdate();
						System.out.println("변경된 row " + r);
						JOptionPane.showMessageDialog(null, "정보수정 완료하였습니다.", "정보수정 성공!", 1);

					} catch (SQLException ex) {
						if (m.getuId().equals("admin")) {
							JOptionPane.showMessageDialog(this, "바꾸실 아이디의 명이 다른 것 같습니다 관리자님.", "정보수정 실패", 0);
						}else {
							JOptionPane.showMessageDialog(this, "Change Failed", "정보수정 실패", 0);
							System.out.println("SQLException" + ex);
						}
					}
				}else {
					JOptionPane.showMessageDialog(this, "현재 로그인한 아이디와 비밀번호가 같지 않습니다.", "정보수정 실패!", 0);
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "두 비밀번호가 다릅니다.", "정보수정 실패!", 0);
			}
			break;
			
		case "계정삭제":
			int select = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?","Confirm",JOptionPane.YES_NO_OPTION);
			if (select == JOptionPane.CLOSED_OPTION) {
				break;
			}else if (select == JOptionPane.YES_OPTION) {
				try {
					String sql = String.format("DELETE FROM user_info WHERE uId = ?");

					SqlConnection sqlConn = new SqlConnection();
					Connection conn = sqlConn.getConnection();

					PreparedStatement pstmt = conn.prepareStatement(sql);


					pstmt.setString(1, personInfoInput[0].getText());

					int r = pstmt.executeUpdate();
					System.out.println("변경된 row " + r);
					JOptionPane.showMessageDialog(null, "계정삭제 완료하였습니다.", "계정삭제 성공!", 1);

				} catch (SQLException ex) {
					if (m.getuId().equals("admin")) {
						JOptionPane.showMessageDialog(this, "삭제하실 아이디의 명이 다른 것 같습니다 관리자님.", "계정삭제 실패", 0);
					}else {
						JOptionPane.showMessageDialog(this, "계정삭제를 실패했습니다.", "계정삭제 실패", 0);
						System.out.println("SQLException" + ex);
					}
				}
			}else {
				break;
			}
			break;
		}
	}

}
