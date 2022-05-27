package Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class PersonalInfo extends JPanel{
	PersonalInfo(){
		setLayout(new BorderLayout());
		TopMenu menu = new TopMenu();
		JPanel infoPanel = new JPanel();
		
		JPanel info_sub_1 = new JPanel();
		JPanel info_sub_2 = new JPanel();
		
		info_sub_1.setLayout(new GridLayout(5,2));
		String[] personInfoText = {"아이디", "비밀번호", "새비밀번호", "새비밀번호 확인", "가게이름"};
		JTextField[] personInfoInput = new JTextField[5];
		for (int i=0; i<personInfoText.length; i++) {
			JPanel newPanel = new JPanel();
			personInfoInput[i] = new JTextField(20);
			newPanel.add(new JLabel(personInfoText[i]));
			newPanel.add(personInfoInput[i]);
			info_sub_1.add(newPanel);
		}
		JButton[] menuInfoBtn = {new JButton("정보수정"),new JButton("계정삭제")};
		for (int i=0; i<menuInfoBtn.length; i++) {
			info_sub_2.add(menuInfoBtn[i]);
		}
		
		
		infoPanel.setLayout(new GridLayout(2,1,20,0));
		infoPanel.add(info_sub_1);
		infoPanel.add(info_sub_2);
		
		add("North",menu);
		add("Center",infoPanel);
		
		this.setVisible(true);
		this.setSize(1920, 1080);
	
	}

}
