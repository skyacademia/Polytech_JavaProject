package Login;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JPanel;

public class TopMenu extends JPanel implements ActionListener {
	public JPanel getMenuPanel() {
		return this;
	}
	TopMenu(){
		setLayout(new GridLayout(1,5));
		
		JButton[] menuBtn = {new JButton("주문"),new JButton("매출 조회"),new JButton("메뉴 관리"),
				new JButton("정보 수정"),new JButton("로그아웃")};
		
		for(int i=0; i<menuBtn.length; i++) {
			menuBtn[i].addActionListener(this);
			add(menuBtn[i]);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton btn  = (JButton)e.getSource();
		Login sp = (Login)((JButton)e.getSource()).getTopLevelAncestor();
		switch(btn.getText()){
		case "주문":
			sp.viewScreen(new Main());
			break;
		case "매출 조회":
			sp.viewScreen(new Sales());
			break;
		case "메뉴 관리":
			try {
				sp.viewScreen(new Menus());
				break;
			} catch (SQLException e1) {}
			
		case "정보 수정":
			sp.viewScreen(new PersonalInfo());
			break;
		case "로그아웃":
			sp.viewScreen(new Sales());
			break;
		}
	}
}
