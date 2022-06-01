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
		
		JButton[] menuBtn = {new JButton("�ֹ�"),new JButton("���� ��ȸ"),new JButton("�޴� ����"),
				new JButton("���� ����"),new JButton("�α׾ƿ�")};
		
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
		case "�ֹ�":
			sp.viewScreen(new Main());
			break;
		case "���� ��ȸ":
			sp.viewScreen(new Sales());
			break;
		case "�޴� ����":
			try {
				sp.viewScreen(new Menus());
				break;
			} catch (SQLException e1) {}
			
		case "���� ����":
			sp.viewScreen(new PersonalInfo());
			break;
		case "�α׾ƿ�":
			sp.viewScreen(new Sales());
			break;
		}
	}
}
