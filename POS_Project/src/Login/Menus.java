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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;

public class Menus extends JPanel {
	Menus() throws SQLException {
		setLayout(new BorderLayout());
		TopMenu menu = new TopMenu();
		JPanel menusPanel = new JPanel();

		menusPanel.setLayout(new GridLayout(1, 2));
		JPanel menus_sub_1 = new JPanel();
		JPanel menus_sub_2 = new JPanel();

		JPanel menus_sub_1_1 = new JPanel();
		JPanel menus_sub_1_2 = new JPanel();

		MenuInfo menu_info = new MenuInfo();
		ArrayList<Object[]> menu_information = menu_info.getMenuInfo();
		HashSet<String> category_text = new HashSet<String>();
		category_text.add("전체");
		for (Object[] menuItem : menu_information) {
			category_text.add((String) menuItem[1]);
		}
		String[] category = category_text.toArray(new String[0]);
		Arrays.sort(category);
		menus_sub_1_1.add(new JLabel("카테고리"));
		JComboBox categoryBox = new JComboBox(category);

		menus_sub_1_1.add(categoryBox);

		menus_sub_1_2.setLayout(new GridLayout(1, 1));
		String[] table_colum = { "코드", "카테고리", "메뉴", "가격" };
		String[][] table_row = new String[menu_information.size()][4];
		for (int i = 0; i < menu_information.size(); i++) {
			Object[] getArray = new Object[4];
			getArray = menu_information.get(i);
			for (int j = 0; j < 4; j++) {
				table_row[i][j] = (String) getArray[j];
			}
		}

		DefaultTableModel dtm = new DefaultTableModel(table_row, table_colum) {
			public boolean isCellEditable(int i, int c) {
				return false;
			}
		};
		JTable table = new JTable(dtm);
		JScrollPane scrollPane = new JScrollPane(table);
		menus_sub_1_2.add(scrollPane);

		categoryBox.addActionListener(new categoryAction(dtm));

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
			menus_sub_2_2.add(menuInfoBtn[i]);
		}

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				JTable jt = (JTable) e.getSource();
				int row = jt.getSelectedRow();
				menuInfoInput[0].setText((String) jt.getValueAt(row, 0));
				menuInfoInput[1].setText((String) jt.getValueAt(row, 1));
				menuInfoInput[2].setText((String) jt.getValueAt(row, 2));
				menuInfoInput[3].setText((String) jt.getValueAt(row, 3));
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
		DefaultTableModel dtm;

		categoryAction(DefaultTableModel dtm) {
			this.dtm = dtm;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox combo = (JComboBox) e.getSource();
			String category_name = combo.getSelectedItem().toString();
			SqlConnection sqlConn;
			try {
				String sql = null;
				ResultSet rs = null;
				sqlConn = new SqlConnection();
				Connection conn = sqlConn.getConnection();
				if (category_name != "전체") {
					sql = "select * from menu_info where mCategory=?";
					PreparedStatement preState = conn.prepareStatement(sql);
					preState.setString(1, category_name);
					rs = preState.executeQuery();
				} else if (category_name.equals("전체")) {
					sql = "select * from menu_info";
					Statement st = conn.createStatement();
					rs = st.executeQuery(sql);
				}
				dtm.setRowCount(0);

				while (rs.next()) {
					String[] addingArray = new String[4];
					addingArray[0] = Integer.toString(rs.getInt("mId"));
					addingArray[1] = rs.getString("mCategory");
					addingArray[2] = rs.getString("mMenu");
					addingArray[3] = Integer.toString(rs.getInt("mPrice"));
					dtm.addRow(addingArray);
				}
			} catch (SQLException e1) {
			}
		}
	}
	class btnAction implements ActionListener{
		DefaultTableModel dtm;
		JTextField[] menuInfoInput;
		String[] addingText = new String[4];
		Connection c;
		btnAction(DefaultTableModel dtm,JTextField[] menuInfoInput) throws SQLException{
			this.dtm = dtm;
			this.menuInfoInput = menuInfoInput;
			SqlConnection sqlc = new SqlConnection();
			c=sqlc.getConnection();
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
				pstat.setInt(1, Integer.parseInt(addingText[0]));
				pstat.setString(2, addingText[1]);
				pstat.setString(3, addingText[2]);
				pstat.setInt(4, Integer.parseInt(addingText[3]));
				pstat.executeUpdate();
			}
			case "수정":{
				String sql = "update menu_info set mCategory=?, mMenu=?, mPrice=? where mId=?";
				PreparedStatement pstat=c.prepareStatement(sql);
				pstat.setString(1, addingText[1]);
				pstat.setString(2, addingText[2]);
				pstat.setInt(3, Integer.parseInt(addingText[3]));
				pstat.setInt(4, Integer.parseInt(addingText[0]));
				pstat.executeUpdate();
			}
			case "삭제":{
				String sql = "delete from menu_info where mId=?";
				PreparedStatement pstat=c.prepareStatement(sql);
				pstat.setInt(1, Integer.parseInt(addingText[0]));
				pstat.executeUpdate();
			}
			}
			} catch (NumberFormatException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

