package Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

public class Menus extends JPanel {
	ArrayList<Object[]> menu_information = new ArrayList<Object[]>();
	Connection c;
	DefaultTableModel tModel;
	MenuInfo menu_info;
	Menus() throws SQLException {
		setLayout(new BorderLayout());
		TopMenu menu = new TopMenu();
		JPanel menusPanel = new JPanel();

		menusPanel.setLayout(new GridLayout(1, 2));
		JPanel menus_sub_1 = new JPanel();
		JPanel menus_sub_2 = new JPanel();

		JPanel menus_sub_1_1 = new JPanel();
		JPanel menus_sub_1_2 = new JPanel();

		menu_info = new MenuInfo();
		c = menu_info.getConnection();
		menu_information = menu_info.getMenuInfo();
		HashSet<String> category_text = new HashSet<String>();
		category_text.add("전체");
		for (Object[] menuItem : menu_information) {
			category_text.add((String) menuItem[1]);
		}
		String[] category = category_text.toArray(new String[0]);
		for (int i=0; i<category.length;i++) {
			if(category[i].equals("전체")) {
				String space = category[0];
				category[0] = category[i];
				category[i] = space;
			}
		}
		Arrays.sort(category,1,category.length);
		menus_sub_1_1.add(new JLabel("카테고리"));
		JComboBox categoryBox = new JComboBox(category);
		menus_sub_1_1.add(categoryBox);
		
		menus_sub_1_2.setLayout(new GridLayout(1, 1));
		String[] table_colum = { "코드", "카테고리", "메뉴", "가격" };
		tModel = new DefaultTableModel(table_colum,0) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		for(int i=0; i<menu_information.size(); i++) {
			tModel.addRow(menu_information.get(i));
		}
		JTable table = new JTable(tModel);		
		JScrollPane scrollPane = new JScrollPane(table);
		menus_sub_1_2.add(scrollPane);

		categoryBox.addActionListener(new categoryAction());

		menus_sub_1.setLayout(new BorderLayout());
		menus_sub_1.add("North", menus_sub_1_1);
		menus_sub_1.add("Center", menus_sub_1_2);

		JPanel menus_sub_2_1 = new JPanel();
		JPanel menus_sub_2_2 = new JPanel();

		menus_sub_2_1.setLayout(new GridLayout(4, 2));
		menus_sub_2_2.setLayout(new GridLayout(1, 3));

		String[] menuInfoText = { "코드", "카테고리", "메뉴이름", "가격" };
		JTextField[] menuInfoInput = new JTextField[4];
		for (int i = 0; i < menuInfoText.length; i++) {
			menuInfoInput[i] = new JTextField();
			menus_sub_2_1.add(new JLabel(menuInfoText[i]));
			menus_sub_2_1.add(menuInfoInput[i]);
		}
//		
//		menuInfoInput[0].setEnabled(false);

		JButton[] menuInfoBtn = { new JButton("추가"), new JButton("수정"), new JButton("삭제") };
		for (int i = 0; i < menuInfoBtn.length; i++) {
			menuInfoBtn[i].addActionListener(new btnAction(menuInfoInput,categoryBox));
			menus_sub_2_2.add(menuInfoBtn[i]);
		}

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JTable jt = (JTable) e.getSource();
				int row = jt.getSelectedRow();
				String name = (String)jt.getValueAt(row, 2);			
				for(Object[] menu : menu_information) {
					if(name.equals((String)menu[2])) {
						menuInfoInput[0].setText(Integer.toString((int)menu[0]));
						menuInfoInput[1].setText((String)menu[1]);
						menuInfoInput[2].setText((String)menu[2]);
						menuInfoInput[3].setText(Integer.toString((int)menu[3]));
					}
				}
			}
		});
		menus_sub_2.setLayout(new BorderLayout());
		menus_sub_2.add("Center", menus_sub_2_1);
		menus_sub_2.add("South", menus_sub_2_2);

		menusPanel.setLayout(new GridLayout(1, 2, 10, 0));
		menusPanel.add(menus_sub_1);
		menusPanel.add(menus_sub_2);

		add("North", menu);
		add("Center", menusPanel);

		this.setVisible(true);
		this.setSize(1920, 1080);
	}
	
	class categoryAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			tModel.setNumRows(0);
			JComboBox combo = (JComboBox) e.getSource();
			String category_name = combo.getSelectedItem().toString();
			
				if(!category_name.equals("전체")) {
					for(Object[] menu : menu_information) {
						if(category_name.equals((String)menu[1])) {
							tModel.addRow(menu);
						}
					}
				}
				else if(category_name.equals("전체")) {
					for(Object[] menu : menu_information) {
						tModel.addRow(menu);
					}
				}
			}
		}
	class btnAction implements ActionListener{
		JTextField[] menuInfoInput;
		Object[] addingText = new Object[4];
		JComboBox categoryBox;
		btnAction(JTextField[] menuInfoInput,JComboBox categoryBox){
			this.menuInfoInput = menuInfoInput;
			this.categoryBox = categoryBox;
		}
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			for(int i=0; i<4; i++) {
				addingText[i] = menuInfoInput[i].getText();
			}
			try {
			switch(btn.getText()) {
			case "추가":{
				String sql = "insert into menu_info values(?,?,?,?)";
				PreparedStatement pstat=c.prepareStatement(sql);
				pstat.setInt(1, Integer.parseInt((String)addingText[0]));
				pstat.setString(2, (String)addingText[1]);
				pstat.setString(3, (String)addingText[2]);
				pstat.setInt(4, Integer.parseInt((String)addingText[3]));
				int change=pstat.executeUpdate();
				if(change>0) {
					JOptionPane.showMessageDialog(null, "메뉴가 추가되었습니다.");
				}
				menu_information.clear();
				menu_information = menu_info.getMenuInfo();
				categoryBox.setSelectedIndex(0);
				break;
			}
			case "수정":{
				String sql = "update menu_info set mCategory=?, mMenu=?, mPrice=? where mId=?";
				PreparedStatement pstat=c.prepareStatement(sql);
				pstat.setString(1, (String)addingText[1]);
				pstat.setString(2, (String)addingText[2]);
				pstat.setInt(3, Integer.parseInt((String)addingText[3]));
				pstat.setInt(4, Integer.parseInt((String)addingText[0]));
				int change=pstat.executeUpdate();
				if(change>0) {
					JOptionPane.showMessageDialog(null, "메뉴가 수정되었습니다.");
				}
				menu_information.clear();
				menu_information = menu_info.getMenuInfo();
				categoryBox.setSelectedIndex(0);
				break;
			}
			case "삭제":{
				String sql = "delete from menu_info where mId=?";
				PreparedStatement pstat=c.prepareStatement(sql);
				pstat.setInt(1, Integer.parseInt((String)addingText[0]));
				int change = pstat.executeUpdate();
				if(change>0) {
					JOptionPane.showMessageDialog(null, "메뉴가 삭제되었습니다.");
				}
				menu_information.clear();
				menu_information = menu_info.getMenuInfo();
				categoryBox.setSelectedIndex(0);
				break;
			}
			}
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	
	}
	public void showMenuTable() {
		tModel.setNumRows(0);
		for (int i = 0; i < menu_information.size(); i++) {
			tModel.addRow(menu_information.get(i));
		}
}
	}

