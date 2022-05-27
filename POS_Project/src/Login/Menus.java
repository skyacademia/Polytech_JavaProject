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

public class Menus extends JPanel {
	Menus(){
		setLayout(new BorderLayout());
		TopMenu menu = new TopMenu();
		JPanel menusPanel = new JPanel();

		menusPanel.setLayout(new GridLayout(1,2));
		JPanel menus_sub_1 = new JPanel();
		JPanel menus_sub_2 = new JPanel();
		
		JPanel menus_sub_1_1 = new JPanel();
		JPanel menus_sub_1_2 = new JPanel();
		
		menus_sub_1_1.add(new JLabel("카테고리"));
		String[] category_text = {"전체","쌀국수","볶음밥"};
		JComboBox categoryBox = new JComboBox(category_text);
		menus_sub_1_1.add(categoryBox);
		
		menus_sub_1_2.setLayout(new GridLayout(1,1));
		String[] table_colum = {"코드","카테고리","메뉴이름","금액"};
		String[][] table_row = {{"1", "쌀국수", "양지쌀국수", "9000"},{"2", "볶음밥", "해산물볶음밥", "12000"}};
		JTable table = new JTable(table_row,table_colum);
		JScrollPane scrollPane = new JScrollPane(table);
		menus_sub_1_2.add(scrollPane);
		
		
		menus_sub_1.setLayout(new BorderLayout());
		menus_sub_1.add("North",menus_sub_1_1);
		menus_sub_1.add("Center",menus_sub_1_2);
		
		JPanel menus_sub_2_1 = new JPanel();
		JPanel menus_sub_2_2 = new JPanel();
		
		menus_sub_2_1.setLayout(new GridLayout(4,2));
		menus_sub_2_2.setLayout(new GridLayout(1,3));
		
		String[] menuInfoText = {"코드", "카테고리", "메뉴 이름", "금액"};
		JTextField[] menuInfoInput = new JTextField[4];
		for (int i=0; i<menuInfoText.length; i++) {
			menuInfoInput[i] = new JTextField();
			menus_sub_2_1.add(new JLabel(menuInfoText[i]));
			menus_sub_2_1.add(menuInfoInput[i]);
		}
		JButton[] menuInfoBtn = {new JButton("추가"),new JButton("수정"),new JButton("삭제")};
		for (int i=0; i<menuInfoBtn.length; i++) {
			menus_sub_2_2.add(menuInfoBtn[i]);
		}
		
		menus_sub_2.setLayout(new BorderLayout());
		menus_sub_2.add("Center",menus_sub_2_1);
		menus_sub_2.add("South",menus_sub_2_2);
		
		menusPanel.setLayout(new GridLayout(1,2,10,0));
		menusPanel.add(menus_sub_1);
		menusPanel.add(menus_sub_2);
		
		add("North",menu);
		add("Center",menusPanel);
		
		this.setVisible(true);
		this.setSize(1920, 1080);
	}
}
