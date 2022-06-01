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
	Font font = new Font("ȸ������", Font.BOLD, 40);

	String id = "", pass = "", passRe = "", name = "", phone = "";
	Login lp;
	public signupPanel() {

		this.setSize(1920, 1080);
		this.setLayout(null);
		
		JLabel idLabel = new JLabel("���̵� : ");
		idLabel.setBounds(836, 131, 48, 15);
		
		JLabel passLabel = new JLabel("��й�ȣ : ");
		passLabel.setBounds(825, 162, 60, 15);
		
		JLabel passReLabel = new JLabel("��й�ȣ ��Ȯ�� : ");
		passReLabel.setBounds(784, 193, 100, 15);
		
		JLabel nameLabel = new JLabel("���� �̸� : ");
		nameLabel.setBounds(820, 224, 64, 15);
		
		JLabel phoneLabel = new JLabel("����ó ��ȣ : ");
		phoneLabel.setBounds(808, 252, 76, 15);

		idTf = new JTextField(15);
		idTf.setBounds(896, 128, 171, 21);
		
		passTf = new JPasswordField(15);
		passTf.setBounds(896, 159, 171, 21);
		
		passReTf = new JPasswordField(15);
		passReTf.setBounds(896, 190, 171, 21);
		
		nameTf = new JTextField(15);
		nameTf.setBounds(896, 221, 171, 21);
		
		phoneTf = new JTextField(11);
		phoneTf.setBounds(896, 249, 171, 21);

		this.add(idLabel);
		this.add(idTf); // ���̵�

		this.add(passLabel);
		this.add(passTf); // pass
		JLabel label = new JLabel("Ư������ + 8��");
		label.setBounds(1071, 162, 90, 15);
		this.add(label); //���ȼ���

		this.add(passReLabel);
		this.add(passReTf); // password ��Ȯ��

		this.add(nameLabel);
		this.add(nameTf); // �̸�

		this.add(phoneLabel);
		this.add(phoneTf);

		JLabel signupLabel = new JLabel("ȸ������ ȭ�� ");
		signupLabel.setBounds(800, 8, 270, 56);
		signupLabel.setFont(font);
		signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		registerButton = new JButton("ȸ������");
		registerButton.setBounds(977, 280, 90, 23);
		registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		this.add(signupLabel);
		this.add(registerButton);

		registerButton.addActionListener(new ActionListener() {      //ȸ�����Թ�ư

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				id = idTf.getText();
				pass = new String(passTf.getPassword());
				passRe = new String(passReTf.getPassword());
				name = nameTf.getText();
				phone = phoneTf.getText();

				String sql = "insert into user_info(uId, uPwd, uStoreName, uPhone, grade) values (?,?,?,?,?)";

				Pattern passPattern1 = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$"); //8�� ����+Ư��+����
				Matcher passMatcher = passPattern1.matcher(pass);

				if (!passMatcher.find()) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ����+Ư������+���� 8�ڷ� �����Ǿ�� �մϴ�", "��й�ȣ ����", 0);
				} else if (!pass.equals(passRe)) {
					JOptionPane.showMessageDialog(null, "��й�ȣ�� ���� ���� �ʽ��ϴ�", "��й�ȣ ����", 0);

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
						System.out.println("����� row " + r);
						JOptionPane.showMessageDialog(null, "ȸ�� ���� �Ϸ�!", "ȸ������", 1);
						Login lps = (Login)((JButton)e.getSource()).getTopLevelAncestor();
						lps.viewScreen(new LoginPanel());
						//�г� ��ȯ �־�ߵ�!
					} catch (SQLException e1) {
						System.out.println("SQL error" + e1.getMessage());
						if (e1.getMessage().contains("PRIMARY")) {
							JOptionPane.showMessageDialog(null, "���̵� �ߺ�!", "���̵� �ߺ� ����", 0);
						} else
							JOptionPane.showMessageDialog(null, "������ ����� �Է����ּ���!", "����", 0);
					} // try ,catch
				}
			}
		});

	}
}